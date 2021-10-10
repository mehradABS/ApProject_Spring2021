import Logic.LogicalAgent;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        LogicalAgent logicalagent = new LogicalAgent();
        while (true) {
            String answer=logicalagent.startingProgram();
            if(answer.equals("0")){
               break;
            }
        }
    }
}