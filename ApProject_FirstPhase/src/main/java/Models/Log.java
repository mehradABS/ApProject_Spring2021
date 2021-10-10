package Models;




import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Log {
    private final String text;
    private final LocalDateTime localDateTime;
    private final int grade;
    private final String username;


    public Log(String text, LocalDateTime localDateTime, int grade, String username)
            throws IOException {
        this.localDateTime = localDateTime;
        this.grade = grade;
        this.username = username;
        this.text = username+": "+text;
    }
    public static void log(Log log) throws IOException {
        FileWriter fileWriter=new FileWriter("src\\Log\\log.txt",
                true);
        fileWriter.write(log.getText()+"\n");
        fileWriter.write(log.getGrade()+"\n");
        fileWriter.write(log.getLocalDateTime().toString()+"\n");
        fileWriter.write(log.getUsername()+"\n");
        fileWriter.write("-----------------------------------------\n");
        fileWriter.flush();
        fileWriter.close();
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public int getGrade() {
        return grade;
    }

    public String getUsername() {
        return username;
    }

}
