package Cli;

public class NewMessage extends Father {


    public void guideLine(String MessageName) {
        System.out.println(ConsoleColors.CYAN+"Please right your " +
                MessageName+" like this :" +"\n"+
                "......"+"\n"
                +"...../"+"\n"+ConsoleColors.RED_BOLD+
                "(/)at the end is" +
                " necessary");
        System.out.println("(if you want to back write(0))"
                +ConsoleColors.RESET);
    }
// kamel nakardam ino hanooz algorithm dar nayovordam

    public String GetMessage() {
        StringBuilder Message=new StringBuilder();
        Message.append(scanner.nextLine());
        if(!Message.toString().equals("0")) {
            while (Message.length() < 2 || Message.charAt(Message.length() - 1) != '/') {
                Message.append("\n").append(scanner.nextLine());
                while (Message.charAt(Message.length() - 1) == ' ') {
                    Message.delete(Message.length() - 1, Message.length());
                }
            }
            for (int i = 0; i < Message.length(); i++) {
                if (Message.charAt(i) == '\n') {
                    Message.delete(i, i + 1);
                    i--;
                } else {
                    break;
                }
            }
            Message.delete(Message.length() - 1, Message.length());
            return Message.toString();
        }
        return "-1";
    }

}