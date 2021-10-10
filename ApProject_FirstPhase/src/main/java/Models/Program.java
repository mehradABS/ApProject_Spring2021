package Models;

import Logic.ModelLoader;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
//meghdardehi bayad beshe
public class Program {
    transient private User currentUser;
    transient private final List<String> allEmails;

    public List<Chat> getAllChats() {
        return allChats;
    }

    transient private final List<Chat> allChats;
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    transient private  final List<String> allPhoneNumbers;
    transient private  final List<User> AllUsers;

    public List<User> getAllUsers() {
        return AllUsers;
    }

    public  boolean checkNewEmail(String username){
        return !allEmails.contains(username);
    }
    public  boolean checkNewPhoneNumber(String username){
        return !allPhoneNumbers.contains(username);
    }
    public List<User> originalUsers(){
        return AllUsers;
    }

    transient private  final List<Tweet> AllTweets;

    public List<Tweet> getAllTweets() {
        return AllTweets;
    }

    transient private  final HashMap<String,String> allInfo;

    public  boolean isValidInfo(String myUsername,String password){
        for (String username: allInfo.keySet()) {
            if(username.equals(myUsername)){
                return allInfo.get(username).equals(password);
            }
        }
        return false;
    }
    public  boolean checkNewUsername(String username){
        return !allInfo.containsKey(username);
    }
    public  boolean checkNewPassword(String password){
        return !allInfo.containsValue(password);
    }

    private final List<Comment> allComments;

    public List<Comment> getAllComments() {
        return allComments;
    }

    public List<Request> getAllRequest() {
        return allRequest;
    }

    transient private final List<Request> allRequest;

    public List<Message> getAllMessages() {
        return allMessages;
    }

    //felan yedoone alaki misazam
    transient private final List<Message> allMessages;

    public List<String> getAllEmails() {
        return allEmails;
    }

    public List<String> getAllPhoneNumbers() {
        return allPhoneNumbers;
    }

    public HashMap<String, String> getAllInfo() {
        return allInfo;
    }

    public Program(){
        this.allEmails = new LinkedList<>();
        this.allPhoneNumbers = new LinkedList<>();
        AllUsers = new LinkedList<>();
        AllTweets = new LinkedList<>();
        this.allInfo = new HashMap<>();
        this.allChats=new LinkedList<>();
        this.reportsId=new HashMap<>();
        this.allRequest=new LinkedList<>();
        allMessages =new LinkedList<>();
        allComments=new LinkedList<>();
    }
    private HashMap<String, String> reportsId;

    public void setReportsId(HashMap<String, String> reportsId) {
        this.reportsId = reportsId;
    }

    public HashMap<String, String> getReportsId() {
        return reportsId;
    }

    public List<Tweet> randomTweets(List<Tweet> allTweets){
        List<Tweet> publicTweets=new LinkedList<>();
        for (Tweet tweet:allTweets) {
            if(tweet.getUser()!=null) {
                if (tweet.getUser().getAccount().getPrivacy().equals("public")
                        && tweet.getUser().getAccount().isActive()
                && !currentUser.getSilentUsers().contains(tweet.getUser())) {
                    publicTweets.add(tweet);
                }
            }
        }
        for (int i = 1; i < publicTweets.size(); i++) {
            for (int j = i; j >0 ; j--) {
                if(publicTweets.get(j).getUsersLikeThis().size()<
                        publicTweets.get(j-1).getUsersLikeThis().size()){
                    Tweet tweet= publicTweets.get(j);
                    publicTweets.set(j,publicTweets.get(j-1));
                    publicTweets.set(j-1,tweet);
                }
            }
        }
        return publicTweets;
    }

    public boolean removeAccount(User user, ModelLoader modelLoader) {
       allInfo.remove(user.getAccount().getUsername());
       AllUsers.remove(user);
       allPhoneNumbers.remove(user.getPhoneNumber());
       allEmails.remove(user.getEmailAddress());
       for (Tweet tweet:user.getMyTweets()) {
            File file=new File("src\\Save\\Tweets\\"+tweet.getId()
                    +".json");
            modelLoader.delete(file);
       }
        for (Request request:user.getMyRequests()) {
            File file=new File("src\\Save\\requests\\" +
                    request.getId()+".json");
            modelLoader.delete(file);
        }
        for (Chat chat:user.getMyChats().keySet()) {
            File file=new File("src\\Save\\Chats\\"+chat.getId()
                    +".json");
            modelLoader.delete(file);
        }
        for (Comment comment:allComments) {
            if(comment.getUser().getId()==user.getId()){
                File file=new File("src\\Save\\comments\\"+comment.getId()
                        +".json");
                modelLoader.delete(file);
            }
        }
        File file=new File("src\\Save\\Users\\"+user.
               getId()+".json");
        modelLoader.delete(file);
        return true;
    }
}
