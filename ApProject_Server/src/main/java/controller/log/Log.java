package controller.log;




import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Log {

    private final String text;
    private final LocalDateTime localDateTime;
    private final int grade;
    private String username;
    private int id;

    public Log(String text, LocalDateTime localDateTime, int grade, String username)
            throws IOException {
        this.localDateTime = localDateTime;
        this.grade = grade;
        this.username = username;
        this.text = username+": "+text;
    }
    public Log(String text, LocalDateTime localDateTime, int grade, int id){
        this.localDateTime = localDateTime;
        this.grade = grade;
        this.id = id;
        this.text = id+": "+text;
    }
    public static void log(Log log) throws IOException {
        FileWriter fileWriter = new FileWriter("src\\Log\\log.txt",
                true);
        fileWriter.write(log.getText()+"\n");
        fileWriter.write(log.getGrade()+"\n");
        fileWriter.write(log.getLocalDateTime().toString()+"\n");
        fileWriter.write(log.getId()+"\n");
        fileWriter.write("-----------------------------------------\n");
        fileWriter.flush();
        fileWriter.close();
    }

    public int getId() {
        return id;
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
