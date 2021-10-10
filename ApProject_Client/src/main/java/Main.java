import config.MainConfig;
import controller.MainController;
import controller.OfflineController;
import resources.ServerAddress;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        new MainConfig();
        MainController mainController;
        try {
            Socket socket = new Socket
                    (ServerAddress.MAIN_ADDRESS, ServerAddress.MAIN_PORT);
            mainController = new MainController(socket);
            OfflineController.IS_ONLINE = true;
        }
        catch (ConnectException e){
            mainController = new MainController();
            OfflineController.IS_ONLINE = false;
            OfflineController.setCurrentUser(1);
        }
        mainController.start();
    }
}