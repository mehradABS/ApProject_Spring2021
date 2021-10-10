package app.auth;


import models.auth.User;
import controller.ClientHandler;
import controller.MainController;
import db.UserDB;
import events.visitors.auth.SetProfileAndBioEventVisitor;
import responses.Response;
import responses.auth.ReceivingImageConfirmationResponse;

import java.io.IOException;

public class GetBioAndImageController extends MainController
        implements SetProfileAndBioEventVisitor {

    private final ClientHandler clientHandler;

    public GetBioAndImageController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public Response getProfileAndBio(String bio, String encodedImage) {
        try {
            if(!"null".equals(encodedImage)) {
                ((UserDB) context.getUsers()).saveProfile(clientHandler.getClientId()
                , encodedImage);
            }
            User user = context.getUsers().get(clientHandler.getClientId());
            user.setBiography(bio);
            context.getUsers().set(user);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new ReceivingImageConfirmationResponse();
    }
}