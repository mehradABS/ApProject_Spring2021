package events.auth;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.auth.SetProfileAndBioEventVisitor;
import responses.Response;


public class SetProfileAndBioEvent extends Event {

    private String bio;
    private String encodedImage;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((SetProfileAndBioEventVisitor)eventVisitor).getProfileAndBio(
                bio, encodedImage);
    }

    @Override
    public String getVisitorType() {
        return "SetProfileAndBioEventVisitor";
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public String getEncodedImage(){
        return encodedImage;
    }
}