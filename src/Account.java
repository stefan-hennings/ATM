public class Account {
    private int accountNumber;
    private double accountBalance;
    private double interestRate = 0;
//    Customer customer;
    
    public Account(double accountBalance, int accountNumber) {
        this.accountBalance = accountBalance;
        this.accountNumber = accountNumber;
    }
    
    public void changeBalance(double change) {
        if (accountBalance + change >= 0) {
            accountBalance += change;
        } else {
            throw new IllegalArgumentException("Ansök om lån!");
        }
    }
    public void changeInterestRate(double newInterest){
        interestRate = newInterest;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }
}
