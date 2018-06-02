package org.vap.webapp.model;

import org.vap.webapp.data.Persistent;

import java.util.List;

/**
 * @author Vahe Pezeshkian
 * May 26, 2018
 */
public class User extends Persistent {
    private String username;
    private String password;
    private String token;
    private boolean confirmed;
    private List<UserLogin> userLogins;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public List<UserLogin> getUserLogins() {
        return userLogins;
    }

    public void setUserLogins(List<UserLogin> userLogins) {
        this.userLogins = userLogins;
    }
}
