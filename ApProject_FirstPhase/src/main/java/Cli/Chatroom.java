package Cli;

import Models.Message;

public class Chatroom extends Father implements CommonActions {

    @Override
    public String GetCommand() {
        while (true) {
            String a = scanner.next();
            switch (a) {
                case "0":
                case "1":
                case "2":
                case "3":
                    return a;
                default:
                    this.invalidCommand();
                    break;
            }
        }
    }

    @Override
    public void showingOptions() {
        System.out.println(ConsoleColors.CYAN +
                "going to four previous messages(1)" +
                ", back to the last messages(2), send a new message(3)");
        System.out.println("write the number of the action which yo want to do" +
                "(Exit thisPage(0))"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED+
                "pay attention, in this page you can not see" +
                ConsoleColors.RED_BOLD+" Tweet's comments"+
                ConsoleColors.RESET);
    }

    public void ShowMessage(String Name,Message message){
        System.out.print(ConsoleColors.GREEN+
                Name+": ");
        System.out.print(ConsoleColors.CYAN+
                message.getText());
        System.out.println("  "+message.getLocalDateTime()+
                ConsoleColors.RESET);
    }
    public void showForwardDetail(String author){
        System.out.println(ConsoleColors.CYAN+
                "Forwarded from: "+author+
                ConsoleColors.RESET);
    }
    public void noMessage(){
        System.out.println("there is no message");
    }
    public void first(){
        System.out.println("first Message");
    }
}