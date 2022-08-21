package GUI;

import db.AdminCRUD;
import models.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    public static boolean loggedIn = false;
    protected static JFrame frame;
    private JPasswordField PasswordField;
    private JOptionPane WarningPane;
    private JPanel MainPanel;
    private JTextField UsernameField;
    private JLabel PasswordLabel;
    private JLabel UsernameLabel;
    private JButton Submit;

    public Login() {
        Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = UsernameField.getText().trim();
                String password = String.valueOf(PasswordField.getPassword()).trim();
                if(id.length() == 0 || password.length() == 0)
                    JOptionPane.showMessageDialog(WarningPane, "Please enter Email and password", "Error", JOptionPane.ERROR_MESSAGE);
                AdminCRUD dao = new AdminCRUD();
                if(dao.isValidAdmin(new Admin(id, password)))
                {
                    loggedIn = true;
                    System.out.println("Logged in successfully");
//                    new Main().setVisible(true);
                    frame.dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(WarningPane, "Invalid Login!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
//                 UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        //            LF.setVisible(true);
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(new Login().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        java.awt.EventQueue.invokeLater(Login::new);
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
}
