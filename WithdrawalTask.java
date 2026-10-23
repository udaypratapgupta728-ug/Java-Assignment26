
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
