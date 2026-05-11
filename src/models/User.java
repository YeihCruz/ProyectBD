package models;

public class User {

    private int userId;
    private int roleId;

    private String username;
    private String password;
    private String fullName;

    private boolean active;

    public User(int userId,
                int roleId,
                String username,
                String password,
                String fullName,
                boolean active) {

        this.userId = userId;
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.active = active;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return username;
    }
}