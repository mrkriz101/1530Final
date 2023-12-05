import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public void closeConnection(Socket sock)
    {
        //send the server Oer word to tell the server we're going to disconnect
        try{
            out = new DataOutputStream(sock.getOutputStream());
            out.writeUTF("Over");
        }
        catch(IOException i){
            System.out.println(i);
        }
        //close the socket
        try {
            sock.close();
        }
        catch (IOException i) {
            System.out.println(i);
        }
    }

    static JPanel contentPanel;
    static JPanel expenseLoggingPanel;
    static JFrame frame;
    static CardLayout cardLayout;

    public static void main(String[] args) {
        String[] userName = new String[1];
        boolean[] pushed = new boolean[1];
        pushed[0] = false;

        //connect to the server
        Money client = new Money();
        Socket socket = client.clientConnection();
        if(socket == null){
            System.out.println("Socket is null");
        }
        else{
            System.out.println("Socket is not null");
        }

        // Create a JFrame
        frame = new JFrame("Money Application");

        JLabel userField = new JLabel("Please Enter Your Username:");
        JButton loginButton = new JButton("Login");
        JPanel loginPanel = new JPanel();
        JTextField user = new JTextField();
        user.setEditable(true);
        user.setPreferredSize(new Dimension(200, 30));

        loginPanel.add(userField);
        loginPanel.add(user);
        loginPanel.add(loginButton);

        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        JPanel budgetPanel = new JPanel();
        budgetPanel.add(new JLabel("Budget Content!"));
        
        expenseLoggingPanel = new JPanel();
        expenseLoggingPanel.setLayout(new GridLayout(5, 2, 5, 5));

        JPanel savingsPanel = new JPanel();
        savingsPanel.add(new JLabel("Savings Content!"));

        contentPanel.add(budgetPanel, "Budget");
        contentPanel.add(expenseLoggingPanel, "ExpenseLogging");
        contentPanel.add(savingsPanel, "Savings");

        JButton budgetButton = new JButton("Budget Planner");
        JButton expenseButton = new JButton("Expense Logging");
        JButton savingsButton = new JButton("Savings Goal");
        JButton homeButton = new JButton("Home");
        JButton exitButton = new JButton("Exit");

        budgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Budget");
            }
        });

        expenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "ExpenseLogging");
                showExpenseLoggingInputs();
            }
        });

        savingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Savings");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.closeConnection(socket);
                System.exit(0);
            }
        });

        JPanel navPanel = new JPanel();
        navPanel.add(budgetButton);
        navPanel.add(expenseButton);
        navPanel.add(savingsButton);
        navPanel.add(exitButton);

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(loginPanel, BorderLayout.NORTH);
        frame.add(navPanel, BorderLayout.SOUTH);
        navPanel.setVisible(false);

        frame.setSize(720, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String curUsername = user.getText();
                // check for existence of user within "database"
                navPanel.setVisible(true);
                loginPanel.setVisible(false);
                userName[0] = curUsername;
                pushed[0] = true;
            }
        });


        System.out.println("Waiting for button to be pushed");
        while(!pushed[0]){
            //System.out.println("Waiting for button to be pushed");
        }
        System.out.println("Button pushed!");
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

    private static void showExpenseLoggingInputs() {
        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Food", "Housing", "Other"});
        JLabel frequencyLabel = new JLabel("Frequency:");
        JTextField frequencyField = new JTextField(10);
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(10);
        JLabel descriptionLabel = new JLabel("Description:");
        JTextArea descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get entered information
                int amount = Integer.parseInt(amountField.getText());
                String category = (String) categoryComboBox.getSelectedItem();
                String frequency = frequencyField.getText();
                String description = descriptionArea.getText();

                // Create Expense object
                Expenses expense = new Expenses(amount, category, frequency, description);

                // Add expense to the list of expenses
                //user.expenses.add(expense);

                // Optional: Print the added expense for verification
                System.out.println("Added Expense: " + expense.getAmount() + " | " +
                                   expense.getCategory() + " | " +
                                   expense.getFrequency() + " | " +
                                   expense.getDescription());

                // Clear input fields after submitting
                amountField.setText("");
                frequencyField.setText("");
                descriptionArea.setText("");
            }
        });

        expenseLoggingPanel.removeAll();
        expenseLoggingPanel.add(categoryLabel);
        expenseLoggingPanel.add(categoryComboBox);
        expenseLoggingPanel.add(frequencyLabel);
        expenseLoggingPanel.add(frequencyField);
        expenseLoggingPanel.add(amountLabel);
        expenseLoggingPanel.add(amountField);
        expenseLoggingPanel.add(descriptionLabel);
        expenseLoggingPanel.add(scrollPane);
        expenseLoggingPanel.add(submitButton); // Add submit button

        frame.revalidate();
        frame.repaint();
    }

}
