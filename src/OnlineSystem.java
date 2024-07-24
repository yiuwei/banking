// Import java packages
import java.io.*;
import java.util.HashMap;
import java.util.Map;

// Define java class to manage user data
public class OnlineSystem {
    private static final String FILE_PATH = "account.txt";
    private final Map<String, UserInfo> customer = new HashMap<>();

    public OnlineSystem() {
        loadUser();
    }

    // Register user to the online bank
    public boolean registerUser(String username, String password, double initialBalance) {
        if (customer.containsKey(username)) {
            return false;
        }
        String hashPassword = AccountSecurity.hashPassword(password);
        String userNumber = createAccount();
        PersonalBank newAccount = new PersonalBank(userNumber, username, initialBalance);
        customer.put(username, new UserInfo(username, hashPassword, newAccount));
        saveUsers();
        return true;
    }

    // Validate the user information
    public UserInfo validateUser(String username, String password) {
        UserInfo userInfo = customer.get(username);
        if (userInfo != null && userInfo.getPassword().equals(AccountSecurity.hashPassword(password))) {
            return userInfo;
        }
        return null;
    }

    public UserInfo getUser(String username) {
        return customer.get(username);
    }

    // Reads and loads the user info to file 
    private void loadUser() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String accountNumber = parts[0];
                    String username = parts[1];
                    String password = parts[2];
                    double balance = Double.parseDouble(parts[3]);

                    PersonalBank account = new PersonalBank(accountNumber, username, balance);
                    UserInfo userInfo = new UserInfo(username, password, account);
                    customer.put(username, userInfo);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error not found", e);
        }
    }

    // Saves the user info to file
    private void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (UserInfo userInfo : customer.values()) {
                PersonalBank account = userInfo.getAccount();
                bw.write(String.format("%s,%s,%s,%.2f%n",
                        account.getNumber(),
                        userInfo.getUsername(),
                        userInfo.getPassword(),
                        account.getBalance()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error not found", e);
        }
    }

    // Generate users account number
    private String createAccount() {
        return String.valueOf((int)(Math.random() * 1000000000));
    }
}
