package com.example.guyazran.usingtablelayout;

/**
 * Created by guyazran on 7/27/15.
 */
public class User {
    public static final String STR_EMPTY = "empty";
    private String username;
    private String password;

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public User(User user) {
        this(user.getUsername(), user.getPassword());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null)
            this.username = STR_EMPTY;
        else
            this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null)
            this.password = STR_EMPTY;
        else
            this.password = password;
    }

    @Override
    public int hashCode() {
        final int u = getUsername().hashCode();
        final int p = getPassword().hashCode();
        return (u * 29) ^ (p * 53) ^ (p * 13);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if(this.username.equals(STR_EMPTY) || this.password.equals(STR_EMPTY))
            return false;
        if (o instanceof User) {
            User other = (User) o;
            return this.getUsername().equals(other.getUsername()) && this.getPassword().equals(other.getPassword());
        }
        return false;
    }
}

