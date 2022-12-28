package fileio;

public final class CredentialsInput {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private int balance;

    public CredentialsInput() {
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getCountry() {
        return country;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "CredentialsInput{"
                + "name='" + name + '\''
                + ", password='" + password + '\''
                + ", accountType='" + accountType + '\''
                + ", country='" + country + '\''
                + ", balance=" + balance
                + '}';
    }
}
