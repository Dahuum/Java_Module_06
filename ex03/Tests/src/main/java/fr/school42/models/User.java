package fr.school42.models;

public class User {
    private Long identifier;
    private String login;
    private String password;
    private boolean authenticationSuccess;

    // Default constructor
    public User() {}

    // Constructor with all fields
    public User(Long identifier, String login, String password, boolean authenticationSuccess) {
        this.identifier = identifier;
        this.login = login;
        this.password = password;
        this.authenticationSuccess = authenticationSuccess;
    }

    public Long getIdentifier() { return identifier; }
    public void setIdentifier(Long identifier) { this.identifier = identifier; }
    
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public boolean isAuthenticationSuccess() { return authenticationSuccess; }
    public void setAuthenticationSuccess(boolean authenticationSuccess) { this.authenticationSuccess = authenticationSuccess; }

}
