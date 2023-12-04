import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Money {
    static JPanel contentPanel;
    static JPanel expenseLoggingPanel;
    static JFrame frame;
    static CardLayout cardLayout;

    public static void main(String[] args) {
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
                navPanel.setVisible(true);
                loginPanel.setVisible(false);
            }
        });
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
