import javax.swing.border.Border;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;



public class Money {
    static JPanel contentPanel;
    static JFrame frame;
    public static void main(String[] args) {
        String userName;

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

        // Create a panel for content
         contentPanel = new JPanel();
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
                //contentArea.setText("Expense logging!");
                showExpenseLoggingInputs();
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
            }
        });
    }
    // Method to display Expense Logging inputs
    private static void showExpenseLoggingInputs() {
        // Get the components needed for Expense Logging
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

        // Modify contentPanel to add and show components for Expense Logging
        contentPanel.setLayout(new GridLayout(5, 2, 5, 5));
        contentPanel.removeAll(); // Clear existing components
        contentPanel.add(categoryLabel);
        contentPanel.add(categoryComboBox);
        contentPanel.add(frequencyLabel);
        contentPanel.add(frequencyField);
        contentPanel.add(amountLabel);
        contentPanel.add(amountField);
        contentPanel.add(descriptionLabel);
        contentPanel.add(scrollPane);

        frame.revalidate(); // Refresh the frame to show updated contentPanel
        frame.repaint();
    }
}