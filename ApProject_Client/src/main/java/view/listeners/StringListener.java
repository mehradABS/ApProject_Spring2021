package view.listeners;

import java.io.IOException;

public interface StringListener {
    void stringEventOccurred(String text) throws IOException;
}