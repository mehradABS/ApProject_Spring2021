package Cli;

import Models.Message;
import Models.User;

import java.util.HashSet;

public class MessageDetail extends Father implements CommonActions {

    @Override
    public String GetCommand() {
        while (true) {
            String a = scanner.next();
            switch (a) {
                case "0":
                case "1":
                case "2":
                    return a;
                default:
                    this.invalidCommand();
                    break;
            }
        }
    }

    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.CYAN_BOLD + "Exit(0)" +
                "watching username of people who like this(1)");
        System.out.println("watching username of people who dislike this(2)"
                + ConsoleColors.RESET);
    }

    public void ShowDetail(Message message) {
        System.out.println(ConsoleColors.GREEN +
                "Was written in "
                + message.getLocalDateTime());
        System.out.println("number of like: "
                + message.getUsersLikeThis().size() + "\n" +
                "number of dislike: "
                + message.getUsersDisLikeThis().size() +
                ConsoleColors.RESET);
    }

    public void showUsername(HashSet<User> users) {
        if (users.size() > 0) {
            for (User user : users) {
                System.out.println(user.getAccount().getUsername());
            }
        }
     else{
            System.out.println("there is no user");
        }
    }
}