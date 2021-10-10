package events.messages;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.messages.NewMessageEventVisitor;
import responses.Response;

public class NewMessageEvent extends Event {

    private String encodedImage;
    private String text;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((NewMessageEventVisitor)eventVisitor).newMessage(this,
                "");
    }

    @Override
    public String getVisitorType() {
        return "NewMessageEventVisitor";
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
