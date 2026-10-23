
class BankAccount {
    private int balance = 1000;

    synchronized void deposit(int amount) {
        balance += amount;

        System.out.println(
            Thread.currentThread().getName()
            + " deposited Rs." + amount
            + " | Balance = Rs." + balance
        );
    }

    synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;

            System.out.println(
                Thread.currentThread().getName()
                + " withdrew Rs." + amount
                + " | Balance = Rs." + balance
            );
        } else {
            System.out.println(
                Thread.currentThread().getName()
                + " tried to withdraw Rs." + amount
                + " | Insufficient Balance"
            );
        }
    }

    synchronized int getBalance() {
        return balance;
    }
}

class DepositTask implements Runnable {
    private BankAccount account;

    DepositTask(BankAccount account) {
        this.account = account;
    }

    public void run() {
        int[] deposits = {500, 700, 300};

        for (int amount : deposits) {
            account.deposit(amount);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("Deposit thread interrupted.");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

class WithdrawalTask implements Runnable {
    private BankAccount account;

    WithdrawalTask(BankAccount account) {
        this.account = account;
    }

    public void run() {
        int[] withdrawals = {400, 600, 500};

        for (int amount : withdrawals) {
            account.withdraw(amount);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("Withdrawal thread interrupted.");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

public class BankTransaction {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        Runnable depositTask = new DepositTask(account);
        Runnable withdrawalTask = new WithdrawalTask(account);

        Thread depositThread = new Thread(depositTask);
        Thread withdrawalThread = new Thread(withdrawalTask);

        depositThread.setName("Deposit Thread");
        withdrawalThread.setName("Withdrawal Thread");

        System.out.println("Bank transaction started.");
        System.out.println(
            "Initial Balance = Rs." + account.getBalance()
        );

        depositThread.start();
        withdrawalThread.start();

        try {
            depositThread.join();
            withdrawalThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
            Thread.currentThread().interrupt();
        }

        System.out.println(
            "Final Balance = Rs." + account.getBalance()
        );
        System.out.println("Bank transaction completed.");
    }
}
