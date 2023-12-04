import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
    
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


}
