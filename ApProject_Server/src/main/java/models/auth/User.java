package models.auth;





import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class User extends OUser{

    public static final Object LOCK = new Object();
    protected final HashSet<Integer> myReqId;
    protected final HashSet<Integer> reqFromMeId;
    protected final List<String> SystemMessages;
    protected final HashSet<Integer> blackUsername;
    protected final HashSet<Integer> followerUsername;
    protected final HashSet<Integer> followingUsername;
    protected final List<ListName> FollowingsList;
    protected final HashSet<Integer> silentUsername;
    protected static int Id_counter;
    protected final HashSet<Integer> favouriteID;
    protected final List<Integer> tweetsID;
    protected final Account account;

    public User(String firstname, String lastname, int year, int month, int day,
                String emailAddress, String phoneNumber, String biography,
                String username, String password) {
        super(firstname, lastname, year, month, day, emailAddress, phoneNumber
        , biography, username, Id_counter + 1);
        Id_counter++;
        blackUsername = new HashSet<>();
        followerUsername = new HashSet<>();
        followingUsername = new HashSet<>();
        FollowingsList = new LinkedList<>();
        myReqId = new HashSet<>();
        reqFromMeId = new HashSet<>();
        SystemMessages = new LinkedList<>();
        tweetsID = new LinkedList<>();
        favouriteID = new HashSet<>();
        silentUsername = new HashSet<>();
        account = new Account(username, password);
    }

    public static void setId_counter(int id_counter) {
        Id_counter = id_counter;
    }

    public List<Integer> getTweetsID() {
        return tweetsID;
    }

    public HashSet<Integer> getFavouriteID(){
        return favouriteID;
    }

    public static int getId_counter() {
        return Id_counter;
    }

    public List<ListName> getFollowingsList() {
        return FollowingsList;
    }

    public List<String> getSystemMessages() {
        return SystemMessages;
    }

    public HashSet<Integer> getBlackUsername() {
        return blackUsername;
    }

    public HashSet<Integer> getFollowerUsername() {
        return followerUsername;
    }

    public HashSet<Integer> getFollowingUsername() {
        return followingUsername;
    }

    public HashSet<Integer> getSilentUsername() {
        return silentUsername;
    }

    public HashSet<Integer> getMyReqId() {
        return myReqId;
    }

    public HashSet<Integer> getReqFromMeId() {
        return reqFromMeId;
    }

    public Account getAccount(){
        return account;
    }

    public void receiveMessage(int chatId, String messageTxt, int userId){
        int b = 0;
        boolean f = true;
        for (int i = 0; i < this.getMyUnreadChatIds().size(); i++) {
            if (this.getMyUnreadChatIds().get(i) == chatId) {
                b = i;
                f = false;
                break;
            }
        }
        if (!f) {
            int c = this.getMyUnreadChatMessagesNumbers().get(b);
            this.getMyUnreadChatMessagesNumbers().set(b, c + 1);
        } else {
            this.getMyUnreadChatIds().add(chatId);
            this.getMyUnreadChatMessagesNumbers().add(1);
        }
    }

    public static class ListName {
        public HashSet<Integer> getUsernames() {
            return usernames;
        }
        private final HashSet<Integer> usernames;
        private String name;
        public ListName() {
            name = "";
            usernames = new HashSet<>();
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }
}
