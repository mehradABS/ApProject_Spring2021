package events.list;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.list.ListPanelEventVisitor;
import responses.Response;

import java.util.List;

public class ListPanelEvent extends Event {

    private String event;
    private String text;
    private String listName;
    private List<Integer> ids;
    private List<String> names;
    private String encodedImage;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ListPanelEventVisitor)eventVisitor).getEvent(this);
    }

    @Override
    public String getVisitorType() {
        return "ListPanelEventVisitor";
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }
}