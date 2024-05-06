package com.mycompany.chatapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String JDBC_URL = "jdbc:derby://localhost:1527/nwpj";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    public static int registerUser(String username, String password) {
        int userId = 0;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                userId = rs.getInt(1);
            }
            System.out.println("User registered successfully. User ID: " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public static boolean checkUser(int userId, String password) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE user_id = ? AND password = ?")) {
            statement.setInt(1, userId);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createProject(String projectName, String projectDescription, String uniqueKey, int userId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO Projects (project_name, project_description, unique_key, user_id) "
                        + "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, projectName);
            statement.setString(2, projectDescription);
            statement.setString(3, uniqueKey);
            statement.setInt(4, userId);
            statement.executeUpdate();

            // Oluşturulan projenin anahtarını al
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                assignUserToProject(userId, uniqueKey); // Kullanıcıyı projeye atama
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void assignUserToProject(int userId, String projectKey) {
        int projectId = 0;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT project_id FROM Projects WHERE unique_key = ?")) {
            statement.setString(1, projectKey);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                projectId = resultSet.getInt("project_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Projenin sahibi değilse ekleme yapma
        if (projectId == 0) {
            System.out.println("Project not found for key: " + projectKey);
            return;
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO User_has_Project (user_id, project_id) "
                        + "VALUES (?, ?)")) {
            statement.setInt(1, userId);
            statement.setInt(2, projectId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getUserProjects(int userId) {
        List<String> projects = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT p.project_name, p.unique_key, "
                        + "(CASE WHEN p.user_id = ? THEN p.unique_key ELSE '' END) AS project_key "
                        + "FROM Projects p "
                        + "JOIN User_has_Project up ON p.project_id = up.project_id "
                        + "WHERE up.user_id = ?")) {
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String projectName = resultSet.getString("project_name");
                String projectKey = resultSet.getString("project_key");
                if (!projectKey.isEmpty()) {
                    projectName += " (" + projectKey + ")";
                }
                projects.add(projectName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public static boolean checkProjectKey(String key) {
        boolean keyExists = false;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT project_id FROM Projects WHERE unique_key = ?")) {
            statement.setString(1, key);
            ResultSet resultSet = statement.executeQuery();
            keyExists = resultSet.next(); // Anahtarın var olup olmadığını kontrol et
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keyExists;
    }

    public static boolean isProjectOwner(int userId, String projectKey) {
        boolean isOwner = false;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM Projects WHERE unique_key = ?")) {
            statement.setString(1, projectKey);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int ownerId = resultSet.getInt("user_id");
                isOwner = (ownerId == userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isOwner;
    }

}
