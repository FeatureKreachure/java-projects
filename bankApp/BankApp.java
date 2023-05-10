import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;

/**
 * BankApp class
 * simple command-line "bank" application
 * user can create accounts, access accounts, and access various account management functions
 * no data is stored so everything will be lost upon termination
 * 
 * NOT INCLUDED: checking that user enters correct datatype
 * eg: program will crash if user enters a String when asked for an int
 */

// class BankApp
public class BankApp{

    // stores <String username, Account account>
    // for storing and accessing current accounts
    private static HashMap<String, Account> accountsMap = new HashMap<>();

    // Scanner
    private static Scanner sc = new Scanner(System.in);

    // account manager method 
    // run when accessing a specific Account object
    public static void accountManager(Account acc){
        // variable to control while loop. Initialised at 0.
        int choice = 0;
        
        // Initial Display
        System.out.println("-----------------------------------------");
        System.out.println("            Account Manager");
        System.out.println("-----------------------------------------");

        // While user does not select option to exit, do:
        // (when choice == 8: loop ends)
        while (choice != 8){
            // Display options
            System.out.println("-----------------------------------------");
            System.out.println("--------------ACCOUNT MENU---------------");
            System.out.println("-----------------------------------------");
            System.out.println("1: View Account Details");
            System.out.println("2: Make A Withdrawal");
            System.out.println("3: Make A Deposit");
            System.out.println("4: View Balance");
            System.out.println("5: Change Username");
            System.out.println("6: Change Account Type");
            System.out.println("7: Change Password");
            System.out.println("8: Exit To Main Menu");
            System.out.println("-----------------------------------------");

            // prompt user for their choice
            System.out.println("Enter Choice:");

            // get nextLine() as string and parse to an int
            // Scanner.nextInt() would get the next integer
            // but won't move the cursor. Causes crash.
            choice = Integer.parseInt(sc.nextLine());

            // switch statement for flow control
            switch (choice){
                case 1:
                    // user wants to view account details
                    showDetails(acc);
                    break;
                case 2:
                    // user wants to make withdrawal
                    makeWithdrawal(acc);
                    break;
                case 3:
                    // user wants to make deposit
                    makeDeposit(acc);
                    break;
                case 4:
                    // user wants to check current balance
                    displayBalance(acc);
                    break;
                case 5:
                    // user wants to change their name
                    changeUserName(acc);
                    break;
                case 6:
                    // user wants to change their account type
                    changeType(acc);
                    break;
                case 7:
                    // user wants to change password
                    changePassword(acc);
                    break;
                case 8:
                    // user wishes to exit
                    // breaks out of loop
                    System.out.println("Logged Out Of Account.\nReturning To Main Menu.");
                    break;
                default:
                    // user has input any other option
                    System.out.println("Try again.");
                    break;
            }
        }
    }

    // method to update password variable in Account object
    public static void changePassword(Account acc){
        // get password from object acc
        String current = acc.getPassword();

        // get new password
        System.out.println("Enter current password:");
        // variable to check that current password matches existing password
        String check = sc.nextLine();

        // "authenticate" password
        while(!check.equals(current) && !check.equals("!cancel")){
            System.out.println("Password doesn't match.\nTry again or enter !cancel to quit.");
            check = sc.nextLine();
        }
        // if user didn't choose to cancel, update password
        if (!check.equals("!cancel")){
            System.out.println("Enter New Password");
            String newPassword = sc.nextLine();
            // set new password in Account object
            acc.setNewPassword(newPassword);
            System.out.println("New Password Set To: " + acc.getPassword());
        }
    }

    // change account type
    public static void changeType(Account acc){
        String type = chooseAccountType();
        // update account variable in object
        acc.changeAccountType(type);
    }

    // change username
    public static void changeUserName(Account acc){
        // store old username to use later when updating key-value pair
        String old = acc.getUsername();

        // ask for new username
        System.out.println("Enter New Username:");
        String newName = sc.nextLine();

        // check if new username already exists
        while (accountsMap.containsKey(newName)){
            System.out.println("That Username Already Exists.\nChoose A Different Name:");
            newName = sc.nextLine();
        }

        // modify key in hashmap
        accountsMap.remove(old);
        // update user variable in Account object
        acc.changeUsername(newName);
        accountsMap.put(newName, acc);

        System.out.println("Username Changed!");
    }

    // get and print all Account object variables except password
    public static void showDetails(Account acc){
        System.out.println("...........................");
        System.out.println("ACCOUNT DETAILS:");
        System.out.println("...........................");
        System.out.println("Account Number: " + acc.getAccNum());
        System.out.println("Username: " + acc.getUsername());
        System.out.println("Account Type: " + acc.getAccountType());
        System.out.println("Current Balance: " + acc.getBalanceAsString());
        System.out.println("...........................");
    }

    // create new Account object and add to accountMap HashMap
    public static void openAccount(){
        // get info from user:

        // account number
        System.out.println("Enter Account Number:");
        String accountNumber = sc.nextLine();

        // password
        System.out.println("Choose Password:");
        String password = sc.nextLine();

        // username
        System.out.println("Enter Username:");
        String user = sc.nextLine();
        // check that username is unique
        while(accountsMap.containsKey(user)){
            System.out.println("Username already exists. Choose another.");
            user = sc.nextLine();
        }

        // calls method to get account type
        String accountType = chooseAccountType();

        // starting balance
        System.out.println("Enter Starting Balance:");
        // parse String to Long
        // avoids curser not moving with Scanner.nextLong()
        long balance = Long.parseLong(sc.nextLine());

        // create and initialise new Account object
        Account account = new Account(accountNumber, password, user, accountType, balance);
        // add Account object to HashMap
        accountsMap.put(account.getUsername(), account);

        System.out.println("Account Created!");
    }

    // choose an account type
    public static String chooseAccountType(){
        System.out.println("Choose Account Type...\n1 - Savings\n2 - Checkings\nEnter Your Choice:\n(default = Checkings)");
        String type = sc.nextLine();
        // check user input
        // if invalid: default to 2
        if (type.equals("1")){
            return "Savings";
        } else if (type.equals("2")){
            return "Checkings";
        } else {
            System.out.println("Invalid input. Default Account = Checkings.\nYou can change this later.");
            return "Checkings";
        }     
    }

    // login method
    public static void login(){
        // ask for account name
        System.out.println("Enter Account Username: ");
        String name = sc.nextLine();
        // method ends and returns to main method if username not found
        if (accountsMap.containsKey(name)){
            // get requested Account object if it exists in the Hashmap
            Account currentAccount = accountsMap.get(name);
            System.out.println("Account found!\nPlease Enter Password:");
            String password = sc.nextLine();
            // "authenticate" password
            if (password.equals(currentAccount.getPassword())){
                System.out.println("Password Accepted!");
                // starts accountManager method
                // on completion, login() will end and will return to while loop in main(String[] args)
                accountManager(currentAccount);
            } else {
                // password doesn't match
                System.out.println("Invalid Password. Please Try Again.");
            }
        } else {
            // incorrect or non-existent username
            System.out.println("Account Not Found. Please Try Again.\nYou'll be returned to the Main Menu.");
        }       
    }


    // subtract requested amount from current balance in Account object
    public static void makeWithdrawal(Account acc){
        // get current balance
        long current = acc.checkBalance();
        System.out.println("Enter Amount To Withdraw:");
        // parse String to Long
        // avoids curser not moving with Scanner.nextLong()
        long amount = Long.parseLong(sc.nextLine());
        // subtract amount if withdrawal doesn't exceed existing balance
        if (amount <= current){
            acc.withdraw(amount);
            System.out.println("Withdrawal Approved.");
        } else {
            System.out.println("You don't have that much to withdraw.");
        }
        // display current balance
        displayBalance(acc);
    }

    // add given long val to balance in Account object    
    public static void makeDeposit(Account acc){
        System.out.println("Enter Amount To Deposit:");
        // parse String to Long
        // avoids curser not moving with Scanner.nextLong()
        long amount = Long.parseLong(sc.nextLine());
        acc.deposit(amount);
        displayBalance(acc);
    }

    // get balance as string and print to screen
    public static void displayBalance(Account acc){
        System.out.println("Current Balance: " + acc.getBalanceAsString());
    }

    // print keys (usernames) in HashMap
    public static void printAccounts(){
        System.out.println("Existing Accounts:");
        // HashMap.keySet returns set of keys
        // for-each loop to iterate through set
        for (String x: accountsMap.keySet()){
            System.out.println(x);
        }
    }

    // main method
    public static void main(String[] args){
        
        // variable for switch statement
        int choice = 0;
        // print header
        System.out.println("-----------------------------------------");
        System.out.println("        WELCOME TO THE BANK APP");
        System.out.println("-----------------------------------------");

        // exit if user inputs 4
        while (choice != 4){
            // print list of options
            System.out.println("-----------------------------------------");
            System.out.println("------------------MENU-------------------");
            System.out.println("-----------------------------------------");
            System.out.println("1: Open Account");
            System.out.println("2: Login To Existing Account");
            System.out.println("3: View Existing Accounts");
            System.out.println("4: Quit App");
            System.out.println("-----------------------------------------");
            System.out.println("Enter Choice:");

            // get nextLine() as string and parse to an int
            // Scanner.nextInt() would get the next integer
            // but won't move the cursor. Causes crash.
            choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    // user wants to create new account
                    openAccount();
                    break;
                case 2:
                    // user wants to login
                    login();
                    break;
                case 3:
                    // show all existing accounts
                    printAccounts();
                    break;
                case 4:
                    // user wants to quit
                    System.out.println("Thanks For Trying This App!");
                    break;
                default:
                    // any other value
                    System.out.println("Please Try Again");
                    break;
            }
        }
    }
}