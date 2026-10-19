package com.example.hotel;

public class User {
    private int userId;  // New field for user ID
    private String username;
    private String email;
    private String nic;
    private String contact;
    private String password;

    // Default constructor
    public User() {
    }

    // Constructor for new user (without userId)
    public User(String username, String email, String nic, String contact, String password) {
        this.username = username;
        this.email = email;
        this.nic = nic;
        this.contact = contact;
        this.password = password;
    }

    // Constructor for updating user (with userId)
    public User(int userId, String username, String email, String nic, String contact, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.nic = nic;
        this.contact = contact;
        this.password = password;
    }

    // Getter and Setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for NIC
    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    // Getter and Setter for contact
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nic='" + nic + '\'' +
                ", contact='" + contact + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    // You may want to override equals and hashCode here if needed.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email); // Assuming email is unique
    }

    @Override
    public int hashCode() {
        return email.hashCode(); // Assuming email is unique
    }
}
