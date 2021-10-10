package models.auth;


import java.time.LocalDateTime;


public class OAccount {

    private String Privacy;
    private LocalDateTime lastSeen;
    private String whoCanSeeLastSeen;
    private boolean IsActive;
    private String Username;

    public OAccount(String username) {
        Username = username;
        IsActive = true;
        Privacy = "public";
        this.whoCanSeeLastSeen = "nobody";
        this.lastSeen = null;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public boolean isActive() {
        return IsActive;
    }

    public String getPrivacy() {
        return Privacy;
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

    public void setPrivacy(String privacy) {
        Privacy = privacy;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setWhoCanSeeLastSeen(String whoCanSeeLastSeen) {
        this.whoCanSeeLastSeen = whoCanSeeLastSeen;
    }
}
