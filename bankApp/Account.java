/**
 * Java Account class for BankApp.java
 * contains constructor and methods
 * to update variables in the class
 */


// class Account
public class Account{
    private String accountNumber;
    private String password;
    private String user;
    private String accountType;
    private long balance;


    // constructor
    public Account(String accountNumber, String password, String user, String accountType, long balance){
        this.accountNumber = accountNumber;
        this.password = password;
        this.user = user;
        this.accountType = accountType;
        this.balance = balance;
    }

    // returns accountType
    public String getAccountType(){
        return accountType;
    }

    // returns username
    public String getUsername(){
        return user;
    }

    // subtracts given long value from existing long balance
    public void withdraw(long withdrawal){
        balance -= withdrawal;
    }

    // adds given long value to existing long balance
    public void deposit(long deposit){
        balance += deposit;
    }

    // return current value stored in long balance
    public long checkBalance(){
        return balance;
    }

    // returns String representation of balance
    public String getBalanceAsString(){
        // in Java a (+ "") (empty string) to a value is the easiest way to convert it to a string
        // example:
        // int n = 2;
        // String num = n + "";
        return balance + "";
    }

    // updates String stored in accountType variable
    public void changeAccountType(String newAccountType){
        accountType = newAccountType;
    }

    // updates String user to newName
    public void changeUsername(String newName){
        user = newName;
    }

    // return account number
    public String getAccNum(){
        return accountNumber;
    }

    // return password
    public String getPassword(){
        return password;
    }

    // update String password to String newPassword
    public void setNewPassword(String newPassword){
        password = newPassword;
    }

}