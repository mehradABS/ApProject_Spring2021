package events.messages;

public class NewTweetEvent extends NewMessageEvent {

    @Override
    public String getVisitorType() {
        return "NewTweetEventVisitor";
    }
}
