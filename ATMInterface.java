import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ATMInterface {
    private JFrame frame;
    private JPanel panel;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton checkBalanceButton;
    private JButton transferFundsButton;
    private JLabel welcomeLabel;
    private AccountManager accountManager;
    private Account currentAccount;

    public ATMInterface() {
        frame = new JFrame("ATM");
        panel = new JPanel();
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        welcomeLabel = new JLabel("Welcome to the ATM");
        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        checkBalanceButton = new JButton("Check Balance");
        transferFundsButton = new JButton("Transfer Funds");

        panel.add(welcomeLabel);
        panel.add(withdrawButton);
        panel.add(depositButton);
        panel.add(checkBalanceButton);
        panel.add(transferFundsButton);

        frame.add(panel);
        frame.setVisible(true);

        accountManager = new AccountManager();
        authenticateUser();
        setupListeners();
    }

    private void authenticateUser() {
        String accountNumber = JOptionPane.showInputDialog(frame, "Enter Account Number:");
        String pin = JOptionPane.showInputDialog(frame, "Enter PIN:");

        if (accountManager.authenticate(accountNumber, pin)) {
            currentAccount = accountManager.getAccount(accountNumber);
            JOptionPane.showMessageDialog(frame, "Authentication Successful");
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Account Number or PIN");
            frame.dispose();
        }
    }

    private void setupListeners() {
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performWithdraw();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDeposit();
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        transferFundsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transferFunds();
            }
        });
    }

    private void performWithdraw() {
        String amountStr = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:");
        double amount = Double.parseDouble(amountStr);

        if (currentAccount.getBalance() >= amount) {
            currentAccount.setBalance(currentAccount.getBalance() - amount);
            JOptionPane.showMessageDialog(frame, "Withdrawal successful. New balance: " + currentAccount.getBalance());
        } else {
            JOptionPane.showMessageDialog(frame, "Insufficient funds.");
        }
    }

    private void performDeposit() {
        String amountStr = JOptionPane.showInputDialog(frame, "Enter amount to deposit:");
        double amount = Double.parseDouble(amountStr);

        currentAccount.setBalance(currentAccount.getBalance() + amount);
        JOptionPane.showMessageDialog(frame, "Deposit successful. New balance: " + currentAccount.getBalance());
    }

    private void checkBalance() {
        JOptionPane.showMessageDialog(frame, "Current balance: " + currentAccount.getBalance());
    }

    private void transferFunds() {
        String targetAccountNumber = JOptionPane.showInputDialog(frame, "Enter target account number:");
        String amountStr = JOptionPane.showInputDialog(frame, "Enter amount to transfer:");
        double amount = Double.parseDouble(amountStr);

        Account targetAccount = accountManager.getAccount(targetAccountNumber);

        if (targetAccount != null && currentAccount.getBalance() >= amount) {
            currentAccount.setBalance(currentAccount.getBalance() - amount);
            targetAccount.setBalance(targetAccount.getBalance() + amount);
            JOptionPane.showMessageDialog(frame, "Transfer successful. New balance: " + currentAccount.getBalance());
        } else {
            JOptionPane.showMessageDialog(frame, "Transfer failed. Check target account number and balance.");
        }
    }

    public static void main(String[] args) {
        new ATMInterface();
    }
}

class AccountManager {
    private Map<String, Account> accounts;

    public AccountManager() {
        accounts = new HashMap<>();
        accounts.put("12345", new Account("12345", "1234", 1000.0));
        accounts.put("67890", new Account("67890", "5678", 500.0)); // Additional account
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean authenticate(String accountNumber, String pin) {
        if (accountNumber == null || pin == null || accountNumber.isEmpty() || pin.isEmpty()) {
            return false;
        }
        Account account = accounts.get(accountNumber);
        return account != null && account.getPin().equals(pin);
    }
}

class Account {
    private String accountNumber;
    private String pin;
    private double balance;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
