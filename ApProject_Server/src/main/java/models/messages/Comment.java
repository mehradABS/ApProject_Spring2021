package models.messages;






import models.auth.User;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Comment extends Message {

    private final List<Integer> commentsId;

    public Comment(String messageType, String text, User user,
                   LocalDateTime localDateTime) {
        super(messageType, text, user, localDateTime);
        this.commentsId=new LinkedList<>();

    }

    public List<Integer> getCommentsId(){
        return commentsId;
    }

    @Override
    public Comment clone() throws CloneNotSupportedException {
        return (Comment)super.clone();
    }
}
