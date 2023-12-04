import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Money extends JFrame {
    private JComboBox<String> categoryComboBox;
    private JTextField amountField;
    private JTextField dateField;
    private JTextArea descriptionArea;
    private JTextArea contentArea;

    public Money() {
        setTitle("Money Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setLocationRelativeTo(null);

        // Create a panel for content
        JPanel contentPanel = new JPanel();
        contentArea = new JTextArea(100, 60);
        contentArea.setEditable(false); // Make the content area read-only
        contentArea.setText("Welcome Nadine!");
        contentPanel.add(contentArea);

        // Create buttons for navigation
        JButton budgetButton = new JButton("Budget Planner");
        JButton expenseButton = new JButton("Expense Logging");
        JButton savingsButton = new JButton("Savings Goal");
        JButton homeButton = new JButton("Home");
        JButton exitButton = new JButton("Exit");

        // Add ActionListener to the home button
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentArea.setText("Welcome Nadine!");
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
                setTitle("Expense Tracker");
                getContentPane().removeAll();
                getContentPane().revalidate();
                getContentPane().repaint();

                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new GridLayout(5, 2, 5, 5));

                JLabel categoryLabel = new JLabel("Category:");
                categoryComboBox = new JComboBox<>(new String[]{"Food", "Home", "Clothes", "Other"});
                mainPanel.add(categoryLabel);
                mainPanel.add(categoryComboBox);

                JLabel amountLabel = new JLabel("Amount:");
                amountField = new JTextField(10);
                mainPanel.add(amountLabel);
                mainPanel.add(amountField);

                JLabel dateLabel = new JLabel("Date (dd/MM/yyyy):");
                dateField = new JTextField(10);
                mainPanel.add(dateLabel);
                mainPanel.add(dateField);

                JLabel descriptionLabel = new JLabel("Description:");
                descriptionArea = new JTextArea(4, 20);
                descriptionArea.setLineWrap(true);
                JScrollPane scrollPane = new JScrollPane(descriptionArea);
                mainPanel.add(descriptionLabel);
                mainPanel.add(scrollPane);

                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new SubmitButtonListener());
                mainPanel.add(submitButton);

                add(mainPanel);
                setVisible(true);
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
        navPanel.add(homeButton);
        navPanel.add(budgetButton);
        navPanel.add(expenseButton);
        navPanel.add(savingsButton);
        navPanel.add(exitButton);

        // Add panels to the frame
        add(contentPanel, BorderLayout.CENTER);
        add(navPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String category = (String) categoryComboBox.getSelectedItem();
            String amountText = amountField.getText();
            String dateText = dateField.getText();
            String description = descriptionArea.getText();

            // Validate inputs (you can add more validation as needed)
            if (amountText.isEmpty() || dateText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter amount and date.");
                return;
            }

            double amount = Double.parseDouble(amountText);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date;
            try {
                date = dateFormat.parse(dateText);
            } catch (java.text.ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please use dd/MM/yyyy.");
                return;
            }

            // Create an object of Expenses
           // Expenses expense = new Expenses((int) amount, category, dateText, description);

            // Process the expense data (you can add your own logic here)
            // For now, just display the entered data
           // String expenseInfo = "Category: " + expense.getCategory() + "\nAmount: $" + expense.getAmount() +
           //         "\nDate: " + expense.getDate() + "\nDescription: " + expense.getDescription();
           // JOptionPane.showMessageDialog(null, "Expense Recorded:\n" + expenseInfo);

            // Clear input fields after submission
            amountField.setText("");
            dateField.setText("");
            descriptionArea.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Money());
    }
}
