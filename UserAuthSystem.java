import java.io.*;
import java.util.Scanner;

public class UserAuthSystem {

    private static final String FILE_NAME = "users.txt"; // File to store user credentials

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        System.out.println("Welcome! Choose an option:");
        System.out.println("1. Create an Account");
        System.out.println("2. Log In");
        System.out.print("Enter choice (1 or 2): ");
        
        int choice = reader.nextInt();
        reader.nextLine(); // Consume newline

        if (choice == 1) {
            registerUser(reader);
        } else if (choice == 2) {
            loginUser(reader);
        } else {
            System.out.println("Invalid choice! Please restart.");
        }

        reader.close();
    }

    // Method to register a new user
    public static void registerUser(Scanner reader) {
        System.out.print("Enter a username: ");
        String username = reader.nextLine();

        // Check if username already exists
        if (checkUserExists(username)) {
            System.out.println("Username already taken! Please try again.");
            return;
        }

        System.out.print("Create a password: ");
        String password = reader.nextLine();
        
        boolean isNumeric = password.matches("\\d+"); // Checks if password contains only digits

        // Save user credentials to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + "," + password);
            writer.newLine();
            System.out.println("\nAccount created successfully!");
            if (isNumeric) {
                System.out.println("⚠️ You set a numeric password.");
            } else {
                System.out.println("✅ You set an alphanumeric password.");
            }
        } catch (IOException e) {
            System.out.println("Error saving account details.");
        }
    }

    // Method to check if a username already exists
    public static boolean checkUserExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username)) {
                    return true; // Username found
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user database.");
        }
        return false;
    }

    // Method to log in a user
    public static void loginUser(Scanner reader) {
        System.out.print("Enter username: ");
        String username = reader.nextLine();
        System.out.print("Enter password: ");
        String password = reader.nextLine();

        // Check credentials from file
        try (BufferedReader readerFile = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = readerFile.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    System.out.println("\n✅ Welcome back, " + username + "!");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error accessing user database.");
        }

        System.out.println("\n❌ Invalid username or password!");
    }
}
