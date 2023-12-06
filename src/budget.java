package src;

import java.util.ArrayList;

public class budget {

    private User user;

    public budget(User user) {
        this.user = user;
    }

    public int calculateSpendingLimit() {
        int totalExpenses = calculateTotalExpenses();
        int savings = user.getSavings();
        int savingsGoal = user.savingsGoal;
        
        // Calculate the available amount for spending
        return savings - (totalExpenses + savingsGoal);
    }

    public int calculateTotalExpenses() {
        int total = 0;
        ArrayList<Expenses> expenses = user.expenses;
        
        for (Expenses expense : expenses) {
            total += expense.getAmount();
        }
        
        return total;
    }
}
