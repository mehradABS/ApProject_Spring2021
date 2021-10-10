package models.auth;





import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class OUser {

    protected String EmailAddress;
    protected String PhoneNumber;
    protected String Biography;
    protected LocalDate Birth;
    protected final List<Integer> myChatIds;
    protected final List<List<Integer>> myChatMessagesIds;
    protected final List<Integer> myUnreadChatMessagesNumbers;
    protected final List<Integer> myUnreadChatIds;
    protected String Firstname;
    protected final int id;
    protected String Lastname;
    private final OAccount OAccount;

    public OUser(String firstname, String lastname, int year, int month, int day,
                 String emailAddress, String phoneNumber, String biography,
                 String username, int id) {
        this.id = id;
        Firstname = firstname;
        Lastname = lastname;
        Birth = LocalDate.of(year, month, day);
        EmailAddress = emailAddress;
        PhoneNumber = phoneNumber;
        Biography = biography;
        this.OAccount = new OAccount(username);
        myChatMessagesIds = new LinkedList<>();
        myChatIds = new LinkedList<>();
        myUnreadChatIds = new LinkedList<>();
        myUnreadChatMessagesNumbers = new LinkedList<>();
    }

    public List<Integer> getMyChatIds() {
        return myChatIds;
    }

    public List<List<Integer>> getMyChatMessagesIds() {
        return myChatMessagesIds;
    }

    public List<Integer> getMyUnreadChatMessagesNumbers() {
        return myUnreadChatMessagesNumbers;
    }

    public List<Integer> getMyUnreadChatIds() {
        return myUnreadChatIds;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public void setBiography(String biography) {
        Biography = biography;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public LocalDate getBirth() {
        return Birth;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getBiography() {
        return Biography;
    }

    public OAccount getAccount() {
        return OAccount;
    }

    public void setBirth(LocalDate birth) {
        Birth = birth;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
