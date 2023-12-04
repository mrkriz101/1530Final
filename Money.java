import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Money {
    public static void main(String[] args) {
        String userName = "Nadine";

        // Create a JFrame
        JFrame frame = new JFrame("Money Application");

        // Create a panel for content
        JPanel contentPanel = new JPanel();
        JTextArea contentArea = new JTextArea(100, 60);
        contentArea.setEditable(false); // Make the content area read-only
        contentArea.setText("Welcome "+userName+"!");
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
                contentArea.setText("Welcome "+userName+"!");
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
        navPanel.add(homeButton);
        navPanel.add(budgetButton);
        navPanel.add(expenseButton);
        navPanel.add(savingsButton);
        navPanel.add(exitButton);

        // Add panels to the frame
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(navPanel, BorderLayout.SOUTH);

        // Set frame properties
        frame.setSize(720, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
