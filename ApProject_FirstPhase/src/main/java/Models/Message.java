package Models;


import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.List;


public class Message {
    private static int Id_counter;
    protected final int Id;
    private final int userId;

    public static int getId_counter() {
        return Id_counter;
    }

    public int getUserId() {
        return userId;
    }

    public static void setId_counter(int id_counter) {
        Id_counter = id_counter;
    }

    public Message(String messageType, String text, User user,
                   LocalDateTime localDateTime) {
        Id_counter++;
        this.Id=Id_counter;
        MessageType = messageType;
        Text = text;
        this.user = user;
        this.localDateTime = localDateTime;
        this.UsersDisLikeThis=new HashSet<>();
        this.UsersLikeThis=new HashSet<>();
        this.like=new HashSet<>();
        this.dislike=new HashSet<>();
        this.userId=user.getId();
    }
    public int getId(){
        return Id;
    }
    public List<Comment> getComments() {
        return null;
    }
    public List<Integer> getCommentsId(){
        return null;
    }
    private final String MessageType;

    private final String Text;
    transient private  User user;

    public void setUser(User user) {
        this.user = user;
    }

    public HashSet<User> getUsersLikeThis() {
        return UsersLikeThis;
    }

    public HashSet<User> getUsersDisLikeThis() {
        return UsersDisLikeThis;
    }

    public HashSet<Integer> getDislike() {
        return dislike;
    }

    public HashSet<Integer> getLike() {
        return like;
    }

    private  HashSet<Integer> like;

    public void setLike(HashSet<Integer> like) {
        this.like = like;
    }

    public void setDislike(HashSet<Integer> dislike) {
        this.dislike = dislike;
    }

    private  HashSet<Integer> dislike;
    transient private  HashSet<User> UsersLikeThis;

    public void setUsersLikeThis(HashSet<User> usersLikeThis) {
        UsersLikeThis = usersLikeThis;
    }

    public void setUsersDisLikeThis(HashSet<User> usersDisLikeThis) {
        UsersDisLikeThis = usersDisLikeThis;
    }

    transient private  HashSet<User> UsersDisLikeThis;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    private final LocalDateTime localDateTime;

    public String getText() {
        return Text;
    }

    public User getUser() {
        return user;
    }

    public String getMessageType() {
        return MessageType;
    }
}