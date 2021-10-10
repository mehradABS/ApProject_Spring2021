package app.chat;



import app.personalPage.subPart.newPost.NewPostPanel;
import controller.OfflineController;
import network.EventListener;
import network.ImageSender;
import resources.Colors;
import resources.Images;
import responses.visitors.ResponseVisitor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class AddPhotoPanel extends NewPostPanel {


    public AddPhotoPanel(EventListener eventListener, HashMap<String, ResponseVisitor>
                          visitors, String name) {
        super(eventListener, visitors, name);
        JButton backButton = new JButton(Images.BACK_ICON);
        backButton.setBounds(10, 10, 54, 45);
        backButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        backButton.setFocusable(false);
        backButton.addActionListener(e -> {
            try {
                resetPanel();
                listenMe("back");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        this.removeAll();
        this.add(imagePanel);
        this.add(pngLabel);
        this.add(backButton);
        this.setBounds(0,0,700,500);
        this.setBackground(Color.decode(Colors.SUB_PANEL));
        OfflineController.ONLINE_PANELS.add(this);
    }

    public void closeImageButtonAction(){
        try {
            checkPhotoController.deleteMessageImage(OfflineController
                    .getContext().getMessages().getAll("message") + 1);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addPhotoButtonAction(){
        pngLabel.setVisible(false);
        int res = chooser.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = checkPhotoController.checkPhoto(OfflineController
                                .getContext().getMessages().getAll("message") + 1,
                        chooser.getSelectedFile().getPath(), 500, 200);
                if(!"null".equals(filePath)) {
                    setImage(new ImageIcon(ImageIO.read(new File(filePath))));
                    imagePanel.repaint();
                    imagePanel.revalidate();
                    encodedImage = ImageSender.encodeImage(filePath);
                    repaint();
                    revalidate();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeState() {

    }

    public void addPanels(){

    }

    public void resetPanel() throws IOException {
        if(imageKeeper.getIcon() != null){
            imageKeeper.setIcon(null);
        }
        imagePanel.setVisible(false);
        tweetButton.setEnabled(false);
        tweetText.setText("");
        defaultText.setVisible(true);
    }

    public void setEncodedImage(String encodedImage){
        this.encodedImage = encodedImage;
    }
}