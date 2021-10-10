package db;


import events.Event;
import models.OChat;
import models.auth.OUser;
import models.messages.OMessage;


public class Context {

    private final DBSet<OUser> users;
    private final MessageDB<OMessage> messages;
    private final DBSet<OChat> chats;

    public Context() {
        this.users = new UserDB();
        this.messages = new MessageDB<>();
        this.chats = new ChatDB();
    }

    public DBSet<OChat> getChats() {
        return chats;
    }

    public DBSet<OUser> getUsers() {
        return users;
    }

    public MessageDB<OMessage> getMessages() {
        return messages;
    }
}
