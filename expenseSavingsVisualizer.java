
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

// Assuming User and Expenses classes are in the same package or imported

public class expenseSavingsVisualizer extends JPanel {
    private User user;

    // Constructor to initialize with a User object
    public expenseSavingsVisualizer(User user) {
        this.user = user;
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Dimensions and offsets
    int panelWidth = getWidth();
    int panelHeight = getHeight();
    int barWidth = 40;
    int maxBarHeight = panelHeight - 100; // Leave space for labels and axis
    int xOffset = panelWidth / 4; // Centering the bars
    int yOffset = 50; // Offset from the top

    // Calculate the scale for the y-axis
    int maxAmount = Math.max(calculateTotalExpenses(), user.getSavings());
    int numMarks = 10; // Number of marks on the y-axis
    int yInterval = maxBarHeight / numMarks; // Interval between y-axis marks

    // Draw y-axis
    g.drawLine(xOffset / 2, yOffset, xOffset / 2, maxBarHeight + yOffset);
    
    // Draw y-axis labels and lines
    g.setColor(Color.BLACK);
    for (int i = 0; i <= numMarks; i++) {
        int yPosition = maxBarHeight + yOffset - (i * yInterval);
        int markerValue = (maxAmount / numMarks) * i;
        g.drawString("$" + markerValue, xOffset / 4, yPosition);
        g.drawLine(xOffset / 2 - 5, yPosition, xOffset / 2 + 5, yPosition); // Draw tick marks
    }

    // Get the total expenses from the User object
    int totalExpenses = calculateTotalExpenses();
    int savings = user.getSavings();

    // Scale the bar heights to the panel size
    int expenseBarHeight = (int)(((double)totalExpenses / maxAmount) * maxBarHeight);
    int savingsBarHeight = (int)(((double)savings / maxAmount) * maxBarHeight);

    // Draw expense bar
    g.setColor(Color.RED);
    g.fillRect(xOffset, maxBarHeight - expenseBarHeight + yOffset, barWidth, expenseBarHeight);
    
    // Draw savings bar
    g.setColor(Color.BLUE);
    g.fillRect(2 * xOffset, maxBarHeight - savingsBarHeight + yOffset, barWidth, savingsBarHeight);

    // Draw labels below bars
    g.setColor(Color.BLACK);
    g.drawString("Expenses: $" + totalExpenses, xOffset, maxBarHeight + yOffset + 30);
    g.drawString("Savings: $" + savings, 2 * xOffset, maxBarHeight + yOffset + 30);
}


    // Calculates the total expenses of the user
    private int calculateTotalExpenses() {
        int total = 0;
        for (Expenses expense : user.expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public static void createAndShowGUI(User user) {
        expenseSavingsVisualizer panel = new expenseSavingsVisualizer(user);
        panel.setPreferredSize(new Dimension(400, 350));

        JFrame frame = new JFrame("Expenses vs Savings Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        User user = new User("John Doe", 5000, 1000);
        user.expenses.add(new Expenses(700, "Rent", "Monthly", "Monthly rent payment"));
        user.expenses.add(new Expenses(200, "Utilities", "Monthly", "Monthly utilities"));
        user.expenses.add(new Expenses(100, "Groceries", "Weekly", "Weekly groceries"));

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(user);
            }
        });
    }
}
