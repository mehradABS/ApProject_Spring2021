package events.visitors.auth;

import events.visitors.EventVisitor;
import responses.Response;

public interface SetProfileAndBioEventVisitor extends EventVisitor {

    Response getProfileAndBio(String bio, String encodedImage);
}