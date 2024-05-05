package CommunicationServer;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private ArrayList<String> projects;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.projects = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public ArrayList<String> getProjects() {
        return projects;
    }
    
    public void addProject(String project) {
        projects.add(project);
    }
    
    public void removeProject(String project) {
        projects.remove(project);
    }
    
}
