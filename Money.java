import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Money {

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null; 
    

    public Socket clientConnection(){
        try{
            socket = new Socket("localhost", 1234);
            System.out.println("Connected");
            return socket;
        }
        catch (UnknownHostException u) {
            System.out.println(u);
            return null;
        }
        catch (IOException i) {
            System.out.println(i);
            return null;
        }
    }

    public static void main(String[] args) {
        String[] userName = new String[1];
        boolean[] pushed = new boolean[1];
        pushed[0] = false;
        // Create a JFrame
        JFrame frame = new JFrame("Money Application");

        JLabel userField = new JLabel("Please Enter Your Username:");
        JButton loginButton = new JButton("Login");
        JPanel loginPanel = new JPanel();
        JTextField user = new JTextField();
        user.setEditable(true);
        user.setPreferredSize(new Dimension(200, 30));

        loginPanel.add(userField);
        loginPanel.add(user);
        loginPanel.add(loginButton);

        // Create a panel for content
        JPanel contentPanel = new JPanel();
        JTextArea contentArea = new JTextArea(100, 60);
        contentArea.setEditable(false); // Make the content area read-only
        contentPanel.add(contentArea);

        // Create buttons for navigation
        JButton budgetButton = new JButton("Budget Planner");
        JButton expenseButton = new JButton("Expense Logging");
        JButton savingsButton = new JButton("Savings Goal");
        JButton homeButton = new JButton("Home");
        JButton exitButton = new JButton("Exit");

        // Add ActionListener to the budget button
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentArea.setText("Welcome "+"!");
            }
        });
        
        // Add ActionListener to the budget button
        budgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentArea.setText("Budget Content!");
            }
        });

        // Add ActionListener to the expense button
        expenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentArea.setText("Expense logging!");
            }
        });

        // Add ActionListener to the savings button
        savingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentArea.setText("Savings Button!");
            }
        });

        // Add ActionListener to the Exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Create a panel for navigation buttons
        JPanel navPanel = new JPanel();
        //navPanel.add(homeButton);
        navPanel.add(budgetButton);
        navPanel.add(expenseButton);
        navPanel.add(savingsButton);
        navPanel.add(exitButton);

        // Add panels to the frame
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(loginPanel, BorderLayout.NORTH);
        frame.add(navPanel, BorderLayout.SOUTH);
        navPanel.setVisible(false);

        // Set frame properties
        frame.setSize(720, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String curUsername = user.getText();
                
                // check for existence of user within "database"
                navPanel.setVisible(true);
                loginPanel.setVisible(false);
                contentArea.setText("Welcome "+curUsername+"!");
                contentArea.setVisible(true);
                userName[0] = curUsername;
                pushed[0] = true;
            }
        });


        

        Money client = new Money();
        Socket socket = client.clientConnection();
        if(socket == null){
            System.out.println("Socket is null");
        }
        else{
            System.out.println("Socket is not null");
        }

        while(!pushed[0]){
            System.out.println("Waiting for button to be pushed");
        }
        
        //send the username to the server
        try{
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(userName[0]);
        }
        catch(IOException i){
            System.out.println(i);
        }
        System.out.println("Username sent to server: " + userName[0]);
        //read the user back from the server
        try {
            InputStream input = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(input);
            
            // Read the object from the stream
            User Actualuser = (User) in.readObject();
            System.out.println("User received from server: " + Actualuser.getName());
            
            in.close(); // Close the ObjectInputStream
        } catch (IOException i) {
            System.out.println("IOException while reading from input stream: " + i.getMessage());
            i.printStackTrace(); // Print the stack trace for more details
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found while deserializing object: " + c.getMessage());
            c.printStackTrace(); // Print the stack trace for more details
        }
        
    
    }
}
