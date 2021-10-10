package Models;

import java.time.LocalDateTime;


public class Account {
    public Account(String username, String password, User user) {
        Username = username;
        Password = password;
        this.user = user;
        IsActive = true;
        Privacy = "public";
        this.whoCanSeeLastSeen="nobody";
        this.lastSeen=null;
    }

    private String Username;

    public void setUsername(String username) {
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }


    public User getUser() {
        return user;
    }

    public boolean isActive() {
        return IsActive;
    }

    public String getPrivacy() {
        return Privacy;
    }


    private String Password;

    public void setPassword(String password) {
        Password = password;
    }

    transient private  User user;

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLastSeen() {
        return this.lastSeen;
    }

    public String getWhoCanSeeLastSeen() {
        return this.whoCanSeeLastSeen;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    private  boolean IsActive;

    public void setPrivacy(String privacy) {
        Privacy = privacy;
    }

    private  String Privacy;
    private  LocalDateTime lastSeen;

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setWhoCanSeeLastSeen(String whoCanSeeLastSeen) {
        this.whoCanSeeLastSeen = whoCanSeeLastSeen;
    }

    private  String whoCanSeeLastSeen;


}
