package Models;
public class Request {
    public void setSourceUser(User sourceUser) {
        SourceUser = sourceUser;
    }

    transient private  User SourceUser;
    private final String text;
    private boolean IsAnswered;
    private boolean IsSystemTellUser;
    private static int Id_counter;
    private final int Id;

    public static void setId_counter(int id_counter) {
        Id_counter = id_counter;
    }

    public static int getId_counter() {
        return Id_counter;
    }

    public int getId() {
        return Id;
    }

    public Request(String text, User user) {
        this.text=text;
        this.SourceUser=user;
        Id_counter++;
        this.Id=Id_counter;
    }

    public boolean isSystemTellUser() {
        return IsSystemTellUser;
    }

    public User getSourceUser() {
        return SourceUser;
    }

    public String getText() {
        return text;
    }

    public void setAnswered(boolean answered) {
        IsAnswered = answered;
    }

    public void setSystemTellUser(boolean systemTellUser) {
        IsSystemTellUser = systemTellUser;
    }

    public void setAccepted(boolean accepted) {
        IsAccepted = accepted;
    }

    public boolean isAnswered() {
        return IsAnswered;
    }

    public boolean isAccepted() {
        return IsAccepted;
    }

    private boolean IsAccepted;
}