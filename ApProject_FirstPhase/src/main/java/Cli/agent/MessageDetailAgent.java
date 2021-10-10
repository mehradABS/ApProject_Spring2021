package Cli.agent;

import Cli.MessageDetail;
import Models.Message;

public class MessageDetailAgent {
    public void MessageDetail(Message message,
                              MessageDetail messageDetail) {
        messageDetail.ShowDetail(message);
        messageDetail.showingOptions();
        while (true) {
            String answer = messageDetail.GetCommand();
            switch (answer) {
                case "0":
                    return;
                case "1":
                    messageDetail.showUsername(message.getUsersLikeThis());
                    break;
                case "2":
                    messageDetail.showUsername(message.getUsersDisLikeThis());
                    break;
            }
        }
    }
}
