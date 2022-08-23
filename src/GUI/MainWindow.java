package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame{
    private JPanel MainMain;
    private JLabel Heading;
    private JTable table1;
    private JTextField getTitle;
    private JButton submit;

    public MainWindow(){

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

//    public void run() {
//
//    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Window");
        frame.setContentPane(new MainWindow().MainMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        java.awt.EventQueue.invokeLater(MainWindow::new);
//        new MainWindow();
    }
}
