package Cli;

import Models.Account;

public class EditPersonalPage extends Father implements CommonActions {

    @Override
    public String GetCommand() {
        while (true) {
            String a = scanner.next();
            switch (a) {
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
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
                "If you want change anything, write its number" +
                "(Exit this page(0))");
    }

    public void ShowInfo(Account account) {
        System.out.println(ConsoleColors.GREEN + "(1)Firstname: " +
                account.getUser().getFirstname() + "   (2)Lastname: " +
                account.getUser().getLastname());
        System.out.println("(3)Username: "
                + account.getUsername() + "   (4)Password: "
                + account.getPassword());
        System.out.println("(5)Birthday: " + account.getUser().getBirth() +
                "   (6)EmailAddress: " + account.getUser().getEmailAddress());
        System.out.println("(7)PhoneNumber: " +
                account.getUser().getPhoneNumber());
        System.out.println("(8)Biography: " +
                account.getUser().getBiography() + ConsoleColors.RESET);
    }

    public String getChange() {
        return this.scanner.next();
    }

    public void change() {
        System.out.println("enter your change(write(0) to Exit)");
    }

    public void Exit() {
        System.out.println("write(0) to Exit");
    }

    public String Biography() {
        System.out.println(ConsoleColors.CYAN + "you can write a biography" +
                " for yourself");
        System.out.println("please write your text like this:" + "\n"
                + "......\n...../\n(/ is necessary)" + ConsoleColors.RESET);
        StringBuilder Biography = new StringBuilder();
        String first=scanner.next();
        Biography.append(first);
        if (!Biography.toString().equals("0")) {
            while (Biography.length() < 3 || Biography.charAt(Biography.length() - 1) != '/') {
                Biography.append("\n").append(scanner.nextLine());
                while (Biography.charAt(Biography.length() - 1) == ' ') {
                    Biography.delete(Biography.length() - 1, Biography.length());
                }
            }
            for (int i = 0; i < Biography.length(); i++) {
                if (Biography.charAt(i) == '\n') {
                    Biography.delete(i, i + 1);
                    i--;
                } else {
                    break;
                }
            }
            Biography.delete(Biography.length() - 1, Biography.length());
        }
        return Biography.toString();
    }

    public String[] Birth() {
        System.out.println(ConsoleColors.CYAN + "Enter your Birth day , " +
                "month , year like this: 10,08,1990" +
                ConsoleColors.RESET);
        String[] Birth;
        String b=scanner.next();
        b=b+',';
        Birth=b.split(",");
        return Birth;
    }
}