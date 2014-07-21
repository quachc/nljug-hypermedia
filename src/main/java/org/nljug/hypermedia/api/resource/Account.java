package org.nljug.hypermedia.api.resource;

/**
 * @author Chris Quach
 */
public class Account {

    private final String accountNumber;
    private final String accountHolder;
    private final double balance;

    public Account(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

}
