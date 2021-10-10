package auth;

public class AuthToken {
    private long authToken = 0;

    public AuthToken() {

    }

    public void setAuthToken(long authToken) {
        this.authToken = authToken;
    }

    public long getAuthToken() {
        return authToken;
    }
}