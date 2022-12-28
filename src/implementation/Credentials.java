package implementation;

import fileio.CredentialsInput;

/**
 * Class differs from CredentialsInput due to the fact that balance is a String, not an int
 */
public final class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;

    public Credentials(final CredentialsInput credentialsInput) {
        this.name = credentialsInput.getName();
        this.password = credentialsInput.getPassword();
        this.accountType = credentialsInput.getAccountType();
        this.country = credentialsInput.getCountry();

        /* Converting int to String */
        this.balance = String.valueOf(credentialsInput.getBalance());
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

    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    public String getCountry() {
        return country;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(final String balance) {
        this.balance = balance;
    }
}
