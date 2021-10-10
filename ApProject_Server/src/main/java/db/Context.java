package db;

import models.*;
import models.auth.User;
import models.messages.Comment;
import models.messages.Message;
import models.messages.Tweet;


public class Context {

    private final DBSet<User> users;
    private final MessageDB<Message> messages;
    private final MessageDB<Tweet> tweets;
    private final MessageDB<Comment> comments;
    private final ProgramDB program;
    private final DBSet<Request> requests;
    private final DBSet<Chat> chats;

    public Context() {
        this.users = new UserDB();
        this.messages = new MessageDB<>();
        this.tweets = new MessageDB<>();
        this.comments = new MessageDB<>();
        this.program = new ProgramDB();
        this.requests = new RequestDB();
        this.chats = new ChatDB();
    }

    public DBSet<Chat> getChats() {
        return chats;
    }

    public ProgramDB getProgram() {
        return program;
    }

    public MessageDB<Comment> getComments() {
        return comments;
    }

    public DBSet<User> getUsers() {
        return users;
    }

    public MessageDB<Message> getMessages() {
        return messages;
    }

    public MessageDB<Tweet> getTweets() {
        return tweets;
    }

    public DBSet<Request> getRequests() {
        return requests;
    }
}
