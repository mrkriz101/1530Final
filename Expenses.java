
public class Expenses {
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
