package models.messages;





import models.auth.User;
import java.time.LocalDateTime;
import java.util.HashSet;


public class Message extends OMessage implements Cloneable{

    public static final Object LOCK = new Object();
    private HashSet<Integer> like;
    private HashSet<Integer> dislike;
    private static int Id_counter;

    public Message(String messageType, String text, User user,
                   LocalDateTime localDateTime) {
        super(messageType, text, user, localDateTime, Id_counter + 1);
        Id_counter++;
        this.like = new HashSet<>();
        this.dislike = new HashSet<>();
    }

    public static int getId_counter() {
        return Id_counter;
    }

    public static void setId_counter(int id_counter) {
        Id_counter = id_counter;
    }

    public HashSet<Integer> getDislike() {
        return dislike;
    }

    public HashSet<Integer> getLike() {
        return like;
    }

    public void setLike(HashSet<Integer> like) {
        this.like = like;
    }

    public void setDislike(HashSet<Integer> dislike) {
        this.dislike = dislike;
    }
}