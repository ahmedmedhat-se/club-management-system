/**
 * Represents a user in the Sports Management System.
 *
 * ROLES: role field can be one of: "ADMIN", "CLUB_LEADER", "MEMBER"
 * Use getRole() to check permissions in the GUI panels.
 */
public class User {

    // ─── Fields ───────────────────────────────────────────────────────────────

    private String userId;
    private String name;
    private String email;
    private String password;
    private String role;     

    // ─── Constructor ──────────────────────────────────────────────────────────

    public User(String userId, String name, String email, String password, String role) {
        this.userId   = userId;
        this.name     = name;
        this.email    = email;
        this.password = password;
        this.role     = role;
    }

    // ─── Methods Implemented ─────────────────────────────────────────────────

    /**
     * Implementation: Validates email and password.
     * Uses equalsIgnoreCase for email to be user-friendly, 
     * but case-sensitive for password.
     */
    public boolean login(String email, String password) {
        if (email == null || password == null) {
            return false;
        }
        return this.email.equalsIgnoreCase(email) && this.password.equals(password);
    }

    /**
     * Implementation: Returns a formatted summary string.
     * Uses the exact format requested in your TODO comment.
     */
    public String getProfile() {
        return "Name: " + name + "\n" +
               "Email: " + email + "\n" +
               "Role: " + role;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public String getUserId()               { return userId; }
    public String getName()                 { return name; }
    public String getEmail()                { return email; }
    public String getPassword()             { return password; }
    public String getRole()                 { return role; }

    public void setUserId(String userId)    { this.userId = userId; }
    public void setName(String name)        { this.name = name; }
    public void setEmail(String email)      { this.email = email; }
    public void setPassword(String password){ this.password = password; }
    public void setRole(String role)        { this.role = role; }

    @Override
    public String toString() {
        return name + " (" + role + ")";
    }
}