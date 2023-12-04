public class Expenses {
    int amount;
    String category;
    String date;
    String description;

    public Expenses(int amount, String category, String date, String description) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDate() {
        return this.date;
    }

    public String getDescription() {
        return this.description;
    }

    
}
