import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainFrame extends JFrame{
  Connection conn;
  Statement stm;
  ResultSet Rs;
  PreparedStatement pst ; 
  public void Connect(){ 
    try{
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/user","root","");
         System.out.println("Connection Etablie");
    }catch(Exception e){
        System.out.println("Erreur de connection");
        e.printStackTrace();
    }
}
    final private Font mainFont = new Font("segoe print",Font.BOLD, 18);
    JTextField firstname,lastname; 
    JLabel Firstname, Lastname ,lbwlcm;
    JPanel  formPanel;
    JButton btnok;
    public void initialize(){
        /* form panel */
         Firstname = new JLabel("First Name ");
         Firstname.setFont(mainFont);
         firstname = new JTextField();
         firstname.setFont(mainFont);

         Lastname = new JLabel("Last Name ");
         Lastname.setFont(mainFont);
         lastname = new JTextField();
         lastname.setFont(mainFont);

         formPanel = new JPanel();
         formPanel.setLayout(new GridLayout(4,1,5,5));
         formPanel.setOpaque(false);
         formPanel.add(Firstname);
         formPanel.add(firstname);
         formPanel.add(Lastname);
         formPanel.add(lastname);
         /*Welcome Label */
         lbwlcm = new JLabel();
         lbwlcm.setFont(mainFont);
         /*************** Buttons Panel  ************** */
          btnok = new JButton("OK");
          btnok.setFont(mainFont);
          btnok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                /*String fname= firstname.getText();
                String lname= lastname.getText();
                lbwlcm.setText("Hello  "+fname+" "+lname);
                */
                try {
                  Connect();
                  String user = firstname.getText();
                  String lastuser = lastname.getText();
                  pst = conn.prepareStatement("INSERT INTO users(name , family_name) VALUES (?,?)");
                  pst.setString(1, firstname.getText().trim());
                  pst.setString(2, lastname.getText().trim());
                  pst.executeUpdate();
                  conn.close();
                  JOptionPane.showMessageDialog(null,  "Vous avez créer un compte avec sucèss","Information",JOptionPane.INFORMATION_MESSAGE);
                  
                } catch (Exception ex) {
                  // TODO: handle exception
                  JOptionPane.showMessageDialog(null, ex.getMessage());
                  ex.printStackTrace();
                }
               
            }
            
          });
          JButton btnclear = new JButton("Clear");
          btnclear.setFont(mainFont);
          btnclear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                firstname.setText("");
                lastname.setText("");
                lbwlcm.setText("");

                 }
            
          });
          JPanel buttonPanel = new JPanel();
          buttonPanel.setLayout(new GridLayout(1,2,5,5));
          buttonPanel.setOpaque(false);
          buttonPanel.add(btnok);
          buttonPanel.add(btnclear);



          JPanel mainPanel = new JPanel();
          mainPanel.setLayout(new BorderLayout());
          mainPanel.setBackground(new Color(128, 128, 255));
          mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
          mainPanel.add(formPanel,BorderLayout.NORTH);
          mainPanel.add(lbwlcm,BorderLayout.CENTER);
          mainPanel.add(buttonPanel,BorderLayout.SOUTH);

          add(mainPanel);
          setTitle("Welcome");
          setSize(500,600);
          setMinimumSize(new Dimension(300, 400));
          setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          setVisible(true);
       
    }
   
 public static void main(String[] args) {
    MainFrame myframe = new MainFrame();
    myframe.initialize();
 }
 
}