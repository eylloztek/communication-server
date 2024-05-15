package com.mycompany.chatapp;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class MainMenu extends javax.swing.JFrame {

    public int userId;
    private DefaultListModel<String> projectListModel = new DefaultListModel<>();

    public MainMenu() {
        initComponents();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_name = new javax.swing.JLabel();
        btn_createProject = new javax.swing.JButton();
        btn_goToProject = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lst_projects = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        btn_refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Menu");
        setMinimumSize(new java.awt.Dimension(500, 500));

        lbl_name.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_name.setText("Welcome to the System!");

        btn_createProject.setText("Create a Project");
        btn_createProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_createProjectActionPerformed(evt);
            }
        });

        btn_goToProject.setText("Go to a Project");
        btn_goToProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_goToProjectActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(lst_projects);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("My Projects");

        btn_refresh.setText("Refresh");
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_createProject, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_goToProject, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(lbl_name)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(129, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(122, 122, 122))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_refresh)
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btn_refresh)
                .addGap(26, 26, 26)
                .addComponent(lbl_name)
                .addGap(41, 41, 41)
                .addComponent(btn_createProject)
                .addGap(27, 27, 27)
                .addComponent(btn_goToProject)
                .addGap(52, 52, 52)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_createProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_createProjectActionPerformed
        openCreateProject();
    }//GEN-LAST:event_btn_createProjectActionPerformed

    private void btn_goToProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_goToProjectActionPerformed
        String key = JOptionPane.showInputDialog(this, "Enter project key:");
        if (key != null && !key.isEmpty()) {
            if (DatabaseManager.checkProjectKey(key)) {
                // Proje anahtarı doğru ise ilgili projeye geç
                if (DatabaseManager.isProjectOwner(userId, key)) {
                    loadUserProjects(); // Kullanıcının sahip olduğu projeleri yeniden yükle
                    openGoToProject(key);
                } else {
                    // Proje sahibi değilse, kullanıcıyı projeye ata ve projeleri yeniden yükle
                    DatabaseManager.assignUserToProject(userId, key);
                    loadUserProjects();
                    openGoToProject(key);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid project key!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_btn_goToProjectActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        loadUserProjects();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void loadUserProjects() {
        projectListModel.clear();

        List<String> projects = DatabaseManager.getUserProjects(userId);
        System.out.println(this.userId);

        for (String project : projects) {
            projectListModel.addElement(project);
        }

        lst_projects.setModel(projectListModel);
    }

    private void openCreateProject() {

        CreateProject createProject = new CreateProject();
        createProject.setUserId(userId);
        createProject.setVisible(true);
        this.setVisible(false);

    }

    private void openGoToProject(String projectKey) {
        GroupChatFrame projectFrame = new GroupChatFrame();
        projectFrame.setUserId(userId);
        projectFrame.setVisible(true);
        this.dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_createProject;
    private javax.swing.JButton btn_goToProject;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_name;
    private javax.swing.JList<String> lst_projects;
    // End of variables declaration//GEN-END:variables
}
