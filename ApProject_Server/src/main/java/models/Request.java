package models;

public class Request {

    private boolean IsAccepted;
    private final String text;
    private boolean IsAnswered;
    private boolean IsSystemTellUser;
    private static int Id_counter;
    private final int Id;
    private final int userId;

    public Request(String text, int id) {
        this.text=text;
        Id_counter++;
        this.Id=Id_counter;
        this.userId = id;
    }

    public static void setId_counter(int id_counter) {
        Id_counter = id_counter;
    }

    public static int getId_counter() {
        return Id_counter;
    }

    public int getId() {
        return Id;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isSystemTellUser() {
        return IsSystemTellUser;
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
}