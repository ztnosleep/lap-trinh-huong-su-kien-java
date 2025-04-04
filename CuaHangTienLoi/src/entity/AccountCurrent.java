package entity;

public class AccountCurrent {
    private static Account currentAccount;

    public static void setCurrentAccount(Account account) {
        currentAccount = account;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void clearSession() {
        currentAccount = null;
    }
}