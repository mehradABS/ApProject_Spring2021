package Cli;

import java.util.Scanner;
//this class is father of all cli classes
public  class Father {
    protected  Scanner scanner=new Scanner(System.in);
    public  void DrawingLine(){
        System.out.println(ConsoleColors.GREEN+
                "----------------------------------------" +
                "----------------------------------------" +
                ConsoleColors.RESET);
    }
    public void finishingProcess(){
        System.out.println(ConsoleColors.GREEN_UNDERLINED+
                "It is Done"+ConsoleColors.RESET);
    }
    public void invalidCommand(){
        System.out.println("invalid command");
    }

    public String ExitProgram(){
        System.out.println("Exit program(0)/login(1)");
        while (true){
            String answer= scanner.next();
            if(answer.equals("0")){
                return "0";
            }
            else if(answer.equals("1")){
                return "1";
            }
            else{
                invalidCommand();
            }
        }
    }
}