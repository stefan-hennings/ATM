import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Customer extends Person implements Serializable {
    private final List<Account> accountList = new ArrayList<>();
    private final List<Loan> loanList = new ArrayList<>();
    private final String customerId;
    
    public Customer(String name, String personalId) {
        super(name, personalId);
        this.customerId = makeRandomCustomerId();
    }
    
    public void accountDeposit() {
        Account account = findAccount();
        double changeBalance = Utility.getDouble("Ange belopp du vill ta sätta in: ");
        account.changeBalance(changeBalance);
        Utility.println("Saldo är nu " + account.getAccountBalance());
    }
    
    public void accountWithdraw() {
        Account account = findAccount();
        double changeBalance = -1 * Utility.getDouble("Ange belopp du vill ta ut: ");
        try {
            account.changeBalance(changeBalance);
        } catch (IllegalArgumentException e) {
            Utility.println(e.getMessage());
        }
        Utility.println("Kontobalans är nu " + account.getAccountBalance());
    }
    
    public void addAccount(double accountBalance, Employee employee) {
        accountList.add(new Account(accountBalance, accountList.size() + 1, employee));
        Bank.serialize();
    }
    
    public void applyForLoan() {
        double loanAmount = Utility.getDouble("Ange lånets storlek: ");
        double interest = Utility.getDouble("Ange lånets räntesats: ");
        
        grantLoan(loanAmount, Bank.activeEmployee, interest);
        Utility.println("Lån på " + getLatestLoan().getDebt() + " skapat åt " + getName());
    }
    
    public void changeAccountInterestRate() {
        Account account = findAccount();
        account.changeInterestRate(Bank.activeEmployee, Utility.getDouble("Ange den nya räntan: "));
    }
    
    /**
     * Method that changes the value of the loan rate
     */
    public void changeInterestRateOnLoan() {
        Loan loan = findLoan();
        double newInterestRate = Utility.getDouble("Mata in nya räntan: ");
        loan.updateInterestRate(newInterestRate);
        Utility.println("\nRäntan är ändrad till " + newInterestRate + "% för lån " + loan.getLoanID() + "\n");
    }
    
    public void createAccount() {
        double depositAmount;
        do {
            depositAmount = Utility.getDouble("Ange belopp att sätta in: ");
        } while (depositAmount < 0);
        addAccount(depositAmount, Bank.activeEmployee);
        Utility.println("Nytt konto skapat med " + accountList.get(accountList.size() - 1).getAccountBalance());
    }
    
    public Account findAccount() {
        while (true) {
            try {
                return getAccount(Utility.getInt("Ange kontonummer: "));
            } catch (ObjectNotFoundException e) {
                Utility.println(e.getMessage());
            }
        }
    }
    
    public Loan findLoan() {
        while (true) {
            try {
                return getLoanFromList(Utility.getInt("Mata in lånID: "));
            } catch (ObjectNotFoundException e) {
                Utility.println(e.getMessage());
            }
        }
    }
    
    public Account getAccount(int accountNumber) {
        for (Account account : accountList) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        throw new ObjectNotFoundException("Kontot finns inte. ");
    }
    
    public List<Account> getAccountList() {
        return accountList;
    }
    
    public Loan getLatestLoan() {
        return loanList.get(loanList.size() - 1);
    }
    
    /**
     * Search for a loan in a list of loans
     *
     * @param loanId ID number of the loan
     *
     * @return Loan
     */
    public Loan getLoanFromList(int loanId) {
        for (Loan loan : loanList) {
            if (loan.getLoanID() == loanId) {
                return loan;
            }
        }
        throw new ObjectNotFoundException("Den här kunden har inget lån med det ID numret. ");
    }
    
    public void grantLoan(double loanAmount, Employee employee, double interestRate) {
        loanList.add(new Loan(loanAmount, employee, interestRate, loanList.size() + 1));
        Bank.serialize();
    }
    
    /**
     * Generate random numbers and put them in to a String
     *
     * @return String of numbers
     */
    private String makeRandomCustomerId() {
        Random random = new Random();
        StringBuilder customerID = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            customerID.append(random.nextInt(9));
        }
        return customerID.toString();
    }
    
    /**
     * Writes a list of all loans for a customer
     */
    public void printAllLoans() {
        Utility.println(getName() + " har totalt " + loanList.size() + " lån hos banken");
        for (Loan currentLoan : loanList) {
            Utility.println("\nLån: " + currentLoan.getLoanID() +
                    "\nTotal skuld: " + currentLoan.getDebt() +
                    "\nRänta på lån: " + currentLoan.getInterestRate() +
                    "\nAnsvarig på banken: " + currentLoan.getManager().getName() + "\n");
        }
    }
    
    /**
     * Writes a list of the history of changes to a loan
     */
    public void printListOfInterestRateChanges() {
        Loan loan = findLoan();
        for (InterestHistory currentLoan : loan.getLoanHistory()) {
            currentLoan.printChanges();
        }
    }
    
    public void printListOfRepayLoanHistory(){
        Loan loan = findLoan();
        System.out.println("Lån från start: " + loan.getStartDebt());
        for (DebtHistory currentLoan : loan.getDeptHistory()) {
            currentLoan.printChanges();
        }
    }
    
    public void repayLoan() {
        Loan loan = findLoan();
        double amountPaid = Utility.getDouble("Hur mycket ska betalas tillbaka? ");
        loan.repay(amountPaid);
    }
    
    public void viewAccountBalance() {
        Account account = findAccount();
        Utility.println("Saldo: " + account.getAccountBalance());
    }
    
    public void viewAllAccounts() {
        if (accountList.isEmpty()) {
            Utility.println("Du har inget konto hos oss.");
        } else {
            for (Account account : accountList) {
                Utility.println("" + account);
            }
        }
    }
}
