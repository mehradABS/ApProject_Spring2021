package models.messages;




import models.auth.OUser;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


public class OMessage {

    protected final LocalDateTime localDateTime;
    protected final String MessageType;
    protected String Text;
    protected int imageId;
    protected HashSet<Integer> retweet_userId;
    protected int forwarderId = -1;
    protected int id;
    protected final int userId;


    public OMessage(String messageType, String text, OUser user,
                    LocalDateTime localDateTime, int id) {
        this.id = id;
        MessageType = messageType;
        Text = text;
        this.localDateTime = localDateTime;
        this.userId = user.getId();
        retweet_userId = new HashSet<>();
        retweet_userId.add(user.getId());
    }

    public int getUserId() {
        return userId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getId(){
        return id;
    }

    public HashSet<Integer> getRetweet_userId() {
        return retweet_userId;
    }

    public void setRetweet_userId(HashSet<Integer> retweet_userId) {
        this.retweet_userId = retweet_userId;
    }

    public List<Integer> getCommentsId(){
        return null;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getText() {
        return Text;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getForwarderId() {
        return forwarderId;
    }

    public void setForwarderId(int forwarderId) {
        this.forwarderId = forwarderId;
    }

    public void setText(String text) {
        Text = text;
    }
}