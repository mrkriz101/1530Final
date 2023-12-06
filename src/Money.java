package src;

import javax.swing.*;

//import Final.User.java;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;



public class Money {
   
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null; 
    static User Actualuser;
    private static JPanel expensesPanel;

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
    private static Object lock = new Object();

    public static void main(String[] args) {
        String[] userName = new String[1];
        boolean[] pushed = new boolean[1];
        pushed[0] = false;
        expensesPanel = new JPanel();
        expensesPanel.setLayout(new BoxLayout(expensesPanel, BoxLayout.Y_AXIS));
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
                showExpenseLoggingInputs(  );
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
                synchronized (lock) {
                    lock.notify();
                }
            }
        });


        System.out.println("Waiting for button to be pushed");
        synchronized (lock) {
            try {
                System.out.println("Waiting for button to be pushed");
                lock.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
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
            Actualuser = (User) in.readObject();
            System.out.println("User received from server: " + Actualuser.getName());
            
            in.close(); // Close the ObjectInputStream

            // Call showExpenseLoggingInputs() after Actualuser is initialized
            showExpenseLoggingInputs( );
        } catch (IOException i) {
            System.out.println("IOException while reading from input stream: " + i.getMessage());
            i.printStackTrace(); // Print the stack trace for more details
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found while deserializing object: " + c.getMessage());
            c.printStackTrace(); // Print the stack trace for more details
        }
        showExpenseLoggingInputs();
    
    }

    private static void showExpenseLoggingInputs() {
         //ArrayList<Expenses> expensesList = new ArrayList<Expenses>();
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
    
        // Create and add input components to the left side panel
        JLabel categoryLabel = new JLabel("Category:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(categoryLabel, gbc);
    
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Food", "Housing", "Other"});
        gbc.gridx = 1;
        inputPanel.add(categoryComboBox, gbc);
    
        JLabel frequencyLabel = new JLabel("Frequency:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(frequencyLabel, gbc);
    
        JTextField frequencyField = new JTextField(10);
        gbc.gridx = 1;
        inputPanel.add(frequencyField, gbc);
    
        JLabel amountLabel = new JLabel("Amount:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(amountLabel, gbc);
    
        JTextField amountField = new JTextField(10);
        gbc.gridx = 1;
        inputPanel.add(amountField, gbc);
    
        JLabel descriptionLabel = new JLabel("Description:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(descriptionLabel, gbc);
    
        JTextArea descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 1;
        inputPanel.add(scrollPane, gbc);
    
        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputPanel.add(submitButton, gbc);
        

    // ActionListener for the submitButton
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
            Actualuser.expenses.add(expense);

            // Clear input fields after submitting
            amountField.setText("");
            frequencyField.setText("");
            descriptionArea.setText("");

            // Update the display of expenses
            updateExpenseDisplay(expensesPanel);
        }
    });//action listener
    // Creating a panel to display the user's expenses
    JPanel expensesPanel = new JPanel();
    expensesPanel.setLayout(new BoxLayout(expensesPanel, BoxLayout.Y_AXIS));

    // Displaying user's expenses on the right side
    updateExpenseDisplay(expensesPanel);

    // Add the input panel to the left side and expensesPanel to the right side
    expenseLoggingPanel.removeAll();
    expenseLoggingPanel.setLayout(new BorderLayout());
    expenseLoggingPanel.add(inputPanel, BorderLayout.WEST);
    expenseLoggingPanel.add(new JScrollPane(expensesPanel), BorderLayout.CENTER);

    updateExpenseDisplay(expensesPanel);

    frame.revalidate();
    frame.repaint();
}

    // Add the input panel to the left side and expensesPanel to the right side
  private static void updateExpenseDisplay(JPanel expensesPanel) {
    System.out.println("updating expenses");
    for (Expenses expense : Actualuser.expenses) {
            System.out.println(expense.getDescription());
        }
    expensesPanel.removeAll();
    if (Actualuser.expenses.isEmpty()) {
        JLabel noExpensesLabel = new JLabel("No expenses");
        expensesPanel.add(noExpensesLabel);
    } else {
        for (Expenses expense : Actualuser.expenses) {
            JLabel expenseLabel = new JLabel("Expense: " + expense.getAmount() + " | " +
                    expense.getCategory() + " | " +
                    expense.getFrequency() + " | " +
                    expense.getDescription());
            expensesPanel.add(expenseLabel);
        }
    }

    expensesPanel.revalidate();
    expensesPanel.repaint();
}
}


//package Final;
class Expenses {
    int amount;
    String category;
    String frequency;
    String description;

    public Expenses(int amount, String category, String frequency, String description) {
        this.amount = amount;
        this.category = category;
        this.frequency = frequency;
        this.description = description;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getCategory() {
        return this.category;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public String getDescription() {
        return this.description;
    }

    
}



 class User implements Serializable{
    
    String name;
    int savings;
    int savingsGoal;
    ArrayList<Expenses> expenses = new ArrayList<Expenses>();

    public User(String name, int savings, int savingsGoal) {
        this.name = name;
        this.savings = savings;
        this.savingsGoal = savingsGoal;
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getSavings() {
        return savings;
    }


}
