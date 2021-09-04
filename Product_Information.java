import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Product_Information {
    private JPanel Main;
    private JTextField textName;
    private JTextField textPrice;
    private JTextField textQty;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Product_Information");
        frame.setContentPane(new Product_Information().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField textpid;
    private JButton searchButton;
    Connection con;
    PreparedStatement pst;

    public Product_Information() {
        connection();


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pname,price,qty;
                pname= textName.getText();
                price=textPrice.getText();
                qty=textQty.getText();

                try {
                    pst = con.prepareStatement("insert into products(pname,price,qty)values(?,?,?)");
                    pst.setString(1,pname);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Added!!!!");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String pid = textpid.getText();
                    pst= con.prepareStatement("select pname,price,qty from products where pid= ?");
                    pst.setString(1,pid);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()==true){
                        String pname = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        textName.setText(pname);
                        textPrice.setText(price);
                        textQty.setText(qty);
                    }else{
                        textName.setText("");
                        textPrice.setText("");
                        textQty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid product ID");
                    }

                } catch (SQLException e2) {
                    e2.printStackTrace();
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pid, pname,price,qty;
                 pname=textName.getText();
                 price=textPrice.getText();
                 qty=textQty.getText();
                 pid=textpid.getText();

                 try{
                     pst = con.prepareStatement("update products set pname = ?,price=?,qty=? where pid = ?");
                     pst.setString(1,pname);
                     pst.setString(2,price);
                     pst.setString(3,qty);
                     pst.setString(4,pid);
                     pst.executeUpdate();
                     JOptionPane.showMessageDialog(null,"Record Updated!!!!");

                     textName.setText("");
                     textPrice.setText("");
                     textQty.setText("");
                     textName.requestFocus();
                     textpid.setText("");

                 } catch (SQLException e3) {
                     e3.printStackTrace();
                 }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid;
                pid= textpid.getText();

                try{
                    pst= con.prepareStatement("delete from products where pid= ?");
                    pst.setString(1,pid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Deleted");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textpid.setText("");
                } catch (SQLException e4) {
                    e4.printStackTrace();
                }
            }
        });
    }




    public void connection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //todo auto-generated catch block
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/gbproducts","root","");
        } catch (SQLException e) {
            //todo auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Database connected sucessfully");
    }

}
