package db;


import models.Report;
import resources.Paths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

import static db.DBSet.gson;

public class ProgramDB {

    public void setReports(Report rp)
            throws IOException {
        FileWriter fileWriter = new FileWriter(Paths.REPORT_PATH_ID_COUNTER,
                false);
        gson.toJson(rp,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public Report getReports() throws IOException {
        File file6 = new File(Paths.REPORT_PATH);
        Report rp = null;
        for (File rpFile : Objects.requireNonNull(file6.listFiles())) {
            FileReader fileReader = new FileReader(rpFile);
            rp = gson.fromJson(fileReader, Report.class);
            fileReader.close();
        }
        if(rp != null)
        return rp;
        rp = new Report();
        rp.setFirstId(new LinkedList<>());
        rp.setLastId(new LinkedList<>());
        return rp;
    }
}