import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ExpenseTracker {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {


        while (true) {
            System.out.println("\n==== Expense Tracker ====");
            System.out.println("1. Add Income/Expense");
            System.out.println("2. Load from File");
            System.out.println("3. Save to File");
            System.out.println("4. View Monthly Summary");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine(); // flush newline

            if (choice == 1) {
                addTransaction();
            } else if (choice == 2) {
                loadFromFile();
            } else if (choice == 3) {
                saveToFile();
            } else if (choice == 4) {
                showSummary();
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    public static void addTransaction() {
        System.out.print("Enter date (yyyy-mm-dd): ");
        String dateStr = sc.nextLine();
        LocalDate date = LocalDate.parse(dateStr);

        System.out.print("Type (Income/Expense): ");
        String type = sc.nextLine();

        String category ="";
        if (type.equalsIgnoreCase("Income")) {
            System.out.print("Category (Salary/Business): ");
        } else {
            System.out.print("Category (Food/Rent/Travel): ");
        }
        category = sc.nextLine();

        System.out.print("Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine(); // flush

        Transaction t = new Transaction(date, type, category, amount);
        transactions.add(t);
        System.out.println("Transaction added!");
    }

    public static void loadFromFile() {
        System.out.print("Enter file path: ");
        String path = sc.nextLine();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                LocalDate date = LocalDate.parse(data[0]);
                String type = data[1];
                String category = data[2];
                double amount = Double.parseDouble(data[3]);
                transactions.add(new Transaction(date, type, category, amount));
            }
            br.close();
            System.out.println("Loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error reading file.");
        }
    }

    public static void saveToFile() {
        System.out.print("Enter file path to save: ");
        String path = sc.nextLine();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (Transaction t : transactions) {
                bw.write(t.toString());
                bw.newLine();
            }
            bw.close();
            System.out.println("Saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    public static void showSummary() {
        HashMap<String, Double> incomeMap = new HashMap<>();
        HashMap<String, Double> expenseMap = new HashMap<>();

        for (Transaction t : transactions) {
            String month = t.date.getYear() + "-" + String.format("%02d", t.date.getMonthValue());

            if (t.type.equalsIgnoreCase("Income")) {
                incomeMap.put(month, incomeMap.getOrDefault(month, 0.0) + t.amount);
            } else {
                expenseMap.put(month, expenseMap.getOrDefault(month, 0.0) + t.amount);
            }

        }

        System.out.println("\n-- Monthly Summary --");
        Set<String> allMonths = new HashSet<>(incomeMap.keySet());
        allMonths.addAll(expenseMap.keySet());

        for (String m : allMonths) {
            double income = incomeMap.getOrDefault(m, 0.0);
            double expense = expenseMap.getOrDefault(m, 0.0);
            System.out.println(m + " -> Income: " + income + ", Expense: " + expense + ", Balance: " + (income - expense));
        }
    }
}
