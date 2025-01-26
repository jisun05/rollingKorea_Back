package history.traveler.rollingkorea.user.domain;

public enum RoleType {

    ROLE_USER("ROLE_USER"),
    ROLE_TESTER("ROLE_TESTER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_DISABLED("ROLE_DISABLED");


    String role;

    RoleType(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
