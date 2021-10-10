package controller.fileHandling;


import controller.OfflineController;

import java.io.IOException;

public class CheckPhotoController extends OfflineController {

    public String checkPhoto(int id, String path, int width, int height) {
        if (path.endsWith(".png")) {
            try {
                context.getMessages().setMessageImage(id, path, width, height);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return context.getMessages().loadMessageImage(id);
        } else {
            return "bad image";
        }
    }

    public void deleteMessageImage(int id){
        context.getMessages().deleteMessageImage(id);
    }
}