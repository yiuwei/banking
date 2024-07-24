// Import java packages
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// Create a user interface for the online banking system
public class PageLayout extends JFrame {
    private final OnlineSystem onlineSystem = new OnlineSystem();
    private UserInfo userInfo;

    private final JTextField username;
    private final JPasswordField password;
    private final JTextField amount;
    private final JTextArea result;

    // Setup JFrame for online bank
    public PageLayout() {
        setTitle("ATM Online System");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to Online Banking", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        setVisible(true);

        // Create JPanel Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4));
        panel.setBackground(new Color(170, 191, 209));
        panel.setBorder(new EmptyBorder(30, 40, 20, 20));

        // Add components to the panel
        panel.add(new JLabel("Username:" ));
        username = new JTextField();
        panel.add(username);

        panel.add(new JLabel("Password:"));
        password = new JPasswordField();
        panel.add(password);

        panel.add(new JLabel("Credit:"));
        amount = new JTextField();
        panel.add(amount);

        // Add panel listener for the buttons
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterEvent());
        panel.add(registerButton);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginEvent());
        panel.add(loginButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new DepositEvent());
        panel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new withdraw());
        panel.add(withdrawButton);

        JButton transferButton = new JButton("ETransfer");
        transferButton.addActionListener(new transfer());
        panel.add(transferButton);

        // Setup user interface with JFrame to display output
        result = new JTextArea();
        result.setEditable(false);
        add(new JScrollPane(result), BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);
    }
    // Setup password length to have a min of 8 characters
    private boolean createPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char pass : password.toCharArray()) {
            if (Character.isLetter(pass)) {
                hasLetter = true;
            } else if (Character.isDigit(pass)) {
                hasDigit = true;
            }
        }
        return hasLetter && hasDigit;
    }
    // Create login class to handle event trigger
    private class LoginEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = PageLayout.this.username.getText();
            String password = new String(PageLayout.this.password.getPassword());
            userInfo = onlineSystem.validateUser(username, password);
            if (userInfo != null) {
                result.setText("Welcome, " + userInfo.getUsername() + "!");
            } else {
                result.setText("Please enter a valid login.");
            }
        }
    }

    // Create register class to handle event trigger
    private class RegisterEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = PageLayout.this.username.getText();
            String password = new String(PageLayout.this.password.getPassword());
            if (!createPassword(password)) {
                result.setText("Password must contain 8 characters.");
                return;
            }
            double balance;
            try {
                balance = Double.parseDouble(amount.getText());
            } catch (NumberFormatException ex) {
                result.setText("Please enter your balance.");
                return;
            }
            boolean register = onlineSystem.registerUser(username, password, balance);
            if (register) {
                result.setText("The account is registered.");
            } else {
                result.setText("Please register again.");
            }
        }
    }
    // Create deposit class to handle event trigger
    private class DepositEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (userInfo == null) {
                result.setText("Please enter the deposit amount.");
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(PageLayout.this.amount.getText());
            } catch (NumberFormatException ex) {
                result.setText("Please enter another amount.");
                return;
            }
            userInfo.getAccount().deposit(amount);
            result.setText("Deposited: $" + amount + ". Total balance: $" + userInfo.getAccount().getBalance() + ".");
        }
    }
    // Create withdraw class to handle event trigger
    private class withdraw implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (userInfo != null) {
                result.setText("Please enter the withdraw amount.");
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(PageLayout.this.amount.getText());
            } catch (NumberFormatException ex) {
                result.setText("Please enter another amount.");
                return;
            }
            boolean success = userInfo.getAccount().withdraw(amount);
            if (success) {
                result.setText("Withdrew: $" + amount + ". Total balance: $" + userInfo.getAccount().getBalance() + ".");
            } else {
                result.setText("Not enough funds.");
            }
        }
    }
    // Create transfer class to handle event trigger
    private class transfer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (userInfo == null) {
                result.setText("Please enter the transfer amount.");
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(PageLayout.this.amount.getText());
            } catch (NumberFormatException ex) {
                result.setText("Please enter another amount.");
                return;
            }
            String username = JOptionPane.showInputDialog("Enter username:");
            UserInfo customer = onlineSystem.getUser(username);
            if (customer == null) {
                result.setText("Username not found.");
                return;
            }
            boolean success = userInfo.getAccount().transfer(customer.getAccount(), amount);
            if (success) {
                result.setText("Transferred: $" + amount + " to " + username + ". Total balance: $" + userInfo.getAccount().getBalance() + ".");
            } else {
                result.setText("Not enough funds.");
            }
        }
    }
}