package Models;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Comment extends Message{

    transient private  List<Comment> comments;

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    private final List<Integer> commentsId;
    public List<Comment> getComments() {
        return comments;
    }
    public List<Integer> getCommentsId(){
        return commentsId;
    }

    public Comment(String messageType, String text, User user,
                   LocalDateTime localDateTime) {
        super(messageType, text, user, localDateTime);
        this.comments=new LinkedList<>();
        this.commentsId=new LinkedList<>();

    }
}
