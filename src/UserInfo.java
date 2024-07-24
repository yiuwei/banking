// Identify properties for UserInfo
public class UserInfo {
    private final String username;
    private final String password;
    private final PersonalBank account;

    // Create users constructor
    public UserInfo(String username, String password, PersonalBank account) {
        this.username = username;
        this.password = password;
        this.account = account;
    }
    // Get username info
    public String getUsername() {
        return username;
    }
    // Get password info
    public String getPassword() {
        return password;
    }
    // Get account info
    public PersonalBank getAccount() {
        return account;
    }
}