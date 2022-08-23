package GUI;

import db.BookCRUD;

import javax.swing.*;

public class MainWindow extends JFrame{
    private JPanel MainMain;
    private JLabel Heading;
    private JTable resultTable;
    private JTextField getTitle;
    private JButton submit;

    private final JOptionPane panelSearchBook;

    public MainWindow(){
        panelSearchBook = new JOptionPane();

        submit.addActionListener(e -> {
            String name = getTitle.getText().trim();
            if(name.length() == 0)
                JOptionPane.showMessageDialog(panelSearchBook, "Please fill in the details", "Error", JOptionPane.ERROR_MESSAGE);
            BookCRUD dao = new BookCRUD();
            System.out.println(dao.getByTitle(name));
            resultTable.setModel(dao.getByTitle(name));
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
