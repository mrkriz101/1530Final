import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Money {
    static JPanel contentPanel;
    static JPanel expenseLoggingPanel;
    static JPanel budgetPanel;
    static JPanel savingsPanel;
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

        budgetPanel = new JPanel();
        budgetPanel.add(new JLabel("Budget Content!"));
        budgetPanel.setVisible(false); // Hide the budgetPanel initially

        expenseLoggingPanel = new JPanel();
        expenseLoggingPanel.setLayout(new GridLayout(5, 2, 5, 5));
        expenseLoggingPanel.setVisible(false); // Hide the expenseLoggingPanel initially

        savingsPanel = new JPanel();
        savingsPanel.add(new JLabel("Savings Content!"));
        savingsPanel.setVisible(false); // Hide the savingsPanel initially

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
                budgetPanel.setVisible(true); // Show the budgetPanel after login
                expenseLoggingPanel.setVisible(true); // Show the expenseLoggingPanel after login
                savingsPanel.setVisible(true); // Show the savingsPanel after login
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

        expenseLoggingPanel.removeAll();
        expenseLoggingPanel.add(categoryLabel);
        expenseLoggingPanel.add(categoryComboBox);
        expenseLoggingPanel.add(frequencyLabel);
        expenseLoggingPanel.add(frequencyField);
        expenseLoggingPanel.add(amountLabel);
        expenseLoggingPanel.add(amountField);
        expenseLoggingPanel.add(descriptionLabel);
        expenseLoggingPanel.add(scrollPane);

        frame.revalidate();
        frame.repaint();
    }
}
