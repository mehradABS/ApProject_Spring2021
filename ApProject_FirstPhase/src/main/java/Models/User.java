package Models;



import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
public class User{
    private static int Id_counter;
    private final int Id;

    public static void setId_counter(int id_counter) {
        Id_counter = id_counter;
    }

    private String Firstname;
    private String Lastname;

    public List<Integer> getTweetsID() {
        return tweetsID;
    }
    public List<Integer> getFavouriteID(){
        return favouriteID;
    }
    private final List<Integer> favouriteID;
    private final List<Integer> tweetsID;
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

    public static int getId_counter() {
        return Id_counter;
    }

    public User(String firstname, String lastname, int year, int month, int day,
                String emailAddress, String phoneNumber, String biography,
                String username, String password) {
        Id_counter++;
        Id = Id_counter;
        Firstname = firstname;
        Lastname = lastname;
        Birth = LocalDate.of(year, month, day);
        EmailAddress = emailAddress;
        PhoneNumber = phoneNumber;
        Biography = biography;
        Blacklist = new LinkedList<>();
        blackUsername=new LinkedList<>();
        Followers = new LinkedList<>();
        followerUsername=new LinkedList<>();
        Followings = new LinkedList<>();
        followingUsername=new LinkedList<>();
        FollowingsList = new LinkedList<>();
        MyRequests = new LinkedList<>();
        RequestsFromMe = new LinkedList<>();
        myReqId=new LinkedList<>();
        reqFromMeId=new LinkedList<>();
        SystemMessages = new LinkedList<>();
        SavedMessage = new LinkedList<>();
        savedMessageId=new LinkedList<>();
        tweetsID=new LinkedList<>();
        MyTweets = new LinkedList<>();
        favouriteID=new LinkedList<>();
        FavouriteTweets = new HashSet<>();
        this.silentUsers = new LinkedList<>();
        silentUsername=new LinkedList<>();
        this.chatID=new LinkedList<>();
        this.myChats = new HashMap<>();

        this.unreadChats = new HashMap<>();
        this.account = new Account(username, password, this);
        this.unreadMessage=new LinkedList<>();

    }

    private final Account account;

    public int getId() {
        return Id;
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

    public List<Tweet> getMyTweets() {
        return MyTweets;
    }

    public List<User> getBlacklist() {
        return Blacklist;
    }

    public List<User> getFollowers() {
     return Followers;
    }

    public List<User> getFollowings() {
        return Followings;
    }

    public List<ListName> getFollowingsList() {
        return FollowingsList;
    }

    public List<Request> getMyRequests() {
        return MyRequests;
    }

    public List<Request> getRequestsFromMe() {
        return RequestsFromMe;
    }

    public List<String> getSystemMessages() {
        return SystemMessages;
    }

    public HashSet<Tweet> getFavouriteTweets() {
        return FavouriteTweets;
    }

    public List<Message> getSavedMessage() {
        return SavedMessage;
    }

    public HashMap<Chat, List<Message>> getMyChats() {
        return myChats;
    }

    public Account getAccount() {
        return account;
    }

    private LocalDate Birth;

    public void setBirth(LocalDate birth) {
        Birth = birth;
    }

    private String EmailAddress;
    private String PhoneNumber;

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    private String Biography;
    transient private  List<Tweet> MyTweets;

    public void setMyTweets(List<Tweet> myTweets) {
        MyTweets = myTweets;
    }

    public void setBlacklist(List<User> blacklist) {
        Blacklist = blacklist;
    }

    transient private  List<User> Blacklist;

    public List<Integer> getBlackUsername() {
        return blackUsername;
    }

    public List<Integer> getFollowerUsername() {
        return followerUsername;
    }

    public List<Integer> getFollowingUsername() {
        return followingUsername;
    }

    public List<Integer> getSilentUsername() {
        return silentUsername;
    }

    private final List<Integer> blackUsername;
    transient private  List<User> Followers;
    private final List<Integer> followerUsername;

    public void setFollowers(List<User> followers) {
        Followers = followers;
    }

    public void setFollowings(List<User> followings) {
        Followings = followings;
    }

    transient private  List<User> Followings;
    private final List<Integer> followingUsername;
    private final List<ListName> FollowingsList;
    transient private  List<User> silentUsers;

    public void setSilentUsers(List<User> silentUsers) {
        this.silentUsers = silentUsers;
    }

    private final List<Integer> silentUsername;


    public List<User> getSilentUsers() {
        return silentUsers;
    }

    public static class ListName {

        public void setList(List<User> list) {
            this.list = list;
        }

        transient private  List<User> list;

        public List<Integer> getUsernames() {
            return usernames;
        }

        private final List<Integer> usernames;
        private String name;

        public ListName() {
            this.list = new LinkedList<>();
            name = "";
            usernames=new LinkedList<>();
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<User> getList() {
           return list;
        }

        public String getName() {
            return name;
        }
    }

    public List<Integer> getMyReqId() {
        return myReqId;
    }

    public List<Integer> getReqFromMeId() {
        return reqFromMeId;
    }

    //
    private final List<Integer> myReqId;
    private final List<Integer> reqFromMeId;
    transient private  List<Request> MyRequests;
    transient private  List<Request> RequestsFromMe;

    public void setMyRequests(List<Request> myRequests) {
        MyRequests = myRequests;
    }

    public void setRequestsFromMe(List<Request> requestsFromMe) {
        RequestsFromMe = requestsFromMe;
    }

    private final List<String> SystemMessages;
    //tartib darkhasta moheme
    //

    //
    //baraye chie
    transient private  HashSet<Tweet> FavouriteTweets;

    public void setFavouriteTweets(HashSet<Tweet> favouriteTweets) {
        FavouriteTweets = favouriteTweets;
    }

    //tweetayi ke like karde too ine
    //
    private final List<Integer> savedMessageId;

    public List<Integer> getSavedMessageId() {
        return savedMessageId;
    }

    transient private  List<Message> SavedMessage;

    public void setSavedMessage(List<Message> savedMessage) {
        SavedMessage = savedMessage;
    }

    transient private  HashMap<Chat, List<Message>> myChats;

    public void setMyChats(HashMap<Chat, List<Message>> myChats) {
        this.myChats = myChats;
    }

    public List<Integer> getChatID() {
        return chatID;
    }

    private final List<Integer> chatID;
    //List<Message> is my messages in each chat.

    transient private  HashMap<Chat, List<Message>> unreadChats;

    public void setUnreadChats(HashMap<Chat, List<Message>> unreadChats) {
        this.unreadChats = unreadChats;
    }

    private final List<Integer> unreadMessage;

    public List<Integer> getUnreadMessage(){
        return unreadMessage;
    }

    public HashMap<Chat, List<Message>> getUnreadChats() {
        return unreadChats;
    }

    public List<Tweet> TimelineTweets() {
        List<Tweet> timeline = new LinkedList<>();
        for (User user : Followings) {
            if (!silentUsers.contains(user)) {
                timeline.addAll(user.getMyTweets());
                for (Tweet tweet:user.getFavouriteTweets()) {
                    if(!timeline.contains(tweet)){
                        timeline.add(tweet);
                    }
                }
            }
        }
        return timeline;
    }

    public HashMap<Chat,Integer> unreadChat(){
        HashMap<Chat,Integer> map=new HashMap<>();
        for (Chat chat :unreadChats.keySet()) {
            if(unreadChats.get(chat).size()>0){
                map.put(chat,unreadChats.get(chat).size());
            }
        }
        return map;
    }
}
