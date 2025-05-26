import java.time.LocalDate;

public class Transaction {
    public LocalDate date;
    public String type;
    public String category;
    public double amount;

    public Transaction(LocalDate date, String type, String category, double amount) {
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
    }

    public String toString() {
        return date + "," + type + "," + category + "," + amount;
    }
}
