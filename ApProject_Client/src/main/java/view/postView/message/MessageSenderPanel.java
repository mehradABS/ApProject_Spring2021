package view.postView.message;

import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import view.listeners.StringListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;

public class MessageSenderPanel extends JPanel {

    private final JButton selectPhotoButton;
    private int editableMessageId;
    private StringListener stringListener;
    private final JButton sendButton;
    private final JTextArea textArea;
    private final JLabel defaultText;
    private final int width;
    private final int y;
    private final JButton exitEdit;

    public MessageSenderPanel(int y,int width) {
        editableMessageId = -1;
        this.y = y;
        this.width = width;
        //
        exitEdit = new JButton(Images.CLOSE_ICON);
        exitEdit.setBounds(width - 115, 10, 54, 45);
        exitEdit.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        exitEdit.setFocusable(false);
        exitEdit.addActionListener(e -> {
            editText(false);
            resetPanel();
        });
        selectPhotoButton = new JButton(Images.SELECT_PHOTO_ICON);
        selectPhotoButton.setBounds(width - 115,10,54,45);
        selectPhotoButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        selectPhotoButton.setFocusable(false);
        selectPhotoButton.addActionListener(e->{
            try {
                photoButtonAction();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        sendButton = new JButton(Images.SEND_ICON);
        sendButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        sendButton.setBounds(width - 60,10,54,45);
        sendButton.addActionListener(e->{
            try {
                sendButtonAction();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        textArea = new JTextArea();
        textArea.setBounds(0,10,width - 120,50);
        textArea.setFont(Fonts.TWEET_FONT);
        textArea.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        textArea.setLineWrap(true);
        //
        defaultText = new JLabel(Texts.MESSAGE);
        defaultText.setFont(Fonts.TWEET_FONT);
        defaultText.setBounds(0, -7, 200, 40);
        textArea.add(defaultText);
        textArea.getDocument().putProperty("name", "TextArea");
        sendButton.setEnabled(false);
        sendButton.setFocusable(false);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                defaultText.setVisible(false);
                sendButton.setEnabled(true);
                textArea.setBounds(0,10,width - 120,Math.max((int) textArea.
                        getPreferredScrollableViewportSize().getHeight(),50));
                setBounds(0,y-
                        (int) textArea.getPreferredScrollableViewportSize()
                                .getHeight() + 25,width,(int) textArea.
                        getPreferredScrollableViewportSize().getHeight() + 40);
                try {
                    listenMe(
                            String.valueOf((int)textArea.
                                    getPreferredScrollableViewportSize()
                            .getHeight() + 25));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                repaint();
                revalidate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textArea.setBounds(0,10,width - 120,Math.max((int) textArea.
                        getPreferredScrollableViewportSize().getHeight(),50));
                setBounds(0,y-
                        (int) textArea.getPreferredScrollableViewportSize()
                                .getHeight() + 25,width,(int) textArea.
                        getPreferredScrollableViewportSize().getHeight() + 40);
                try {
                    listenMe(String.valueOf((int)textArea.getPreferredScrollableViewportSize()
                            .getHeight() + 25));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                repaint();
                revalidate();
                if (textArea.getText().isEmpty()) {
                    defaultText.setVisible(true);
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
        this.setBounds(0,y,width,80);
        this.add(textArea);
        this.add(sendButton);
        this.add(selectPhotoButton);
    }

    public void resetPanel(){
        editableMessageId = -1;
        textArea.setText("");
        sendButton.setEnabled(false);
        textArea.setBounds(0,10,width - 120,50);
        this.setBounds(0,y,width,80);
        repaint();
        revalidate();
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
        stringListener.stringEventOccurred(name);
    }

    public void sendButtonAction() throws IOException {
        listenMe("send");
        resetPanel();
    }
    public void photoButtonAction() throws IOException {
        listenMe("photo");
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void editText(boolean edit){
        if(edit){
            this.remove(selectPhotoButton);
            this.add(exitEdit);
        }
        else{
            this.add(selectPhotoButton);
            this.remove(exitEdit);
            textArea.setText("");
        }
        repaint();
        revalidate();
    }

    public int getEditableMessageId() {
        return editableMessageId;
    }

    public void setEditableMessageId(int editableMessageId) {
        this.editableMessageId = editableMessageId;
    }
}
