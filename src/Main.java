public class Main {
    public static void main(String[] args) {
        // 1. Create a new User instance (MEMBER role)
        User testUser = new User(
            "USR-001", 
            "Ahmed", 
            "ahmed@email.com", 
            "securePass123", 
            "MEMBER"
        );

        System.out.println("--- Testing Sports Management System: User Class ---");

        // 2. Test getProfile() method
        System.out.println("\n[Testing Profile Display]");
        System.out.println(testUser.getProfile());

        // 3. Test login() method - Successful attempt
        System.out.println("\n[Testing Login - Correct Credentials]");
        boolean success = testUser.login("ahmed@email.com", "securePass123");
        System.out.println("Login status: " + (success ? "SUCCESS ✅" : "FAILED ❌"));

        // 4. Test login() method - Wrong password
        System.out.println("\n[Testing Login - Wrong Password]");
        boolean wrongPass = testUser.login("ahmed@email.com", "wrongPassword");
        System.out.println("Login status: " + (wrongPass ? "SUCCESS ✅" : "FAILED ❌"));

        // 5. Test login() method - Case Insensitivity for Email
        System.out.println("\n[Testing Login - Case Insensitive Email]");
        boolean caseEmail = testUser.login("AHMED@email.com", "securePass123");
        System.out.println("Login status: " + (caseEmail ? "SUCCESS ✅" : "FAILED ❌"));

        // 6. Test toString() override
        System.out.println("\n[Testing toString Output]");
        System.out.println("User Object: " + testUser.toString());
    }
}