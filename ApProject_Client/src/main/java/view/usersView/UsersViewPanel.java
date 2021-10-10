package view.usersView;

import resources.Colors;
import view.listeners.StringListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class UsersViewPanel<T extends UserPanel> extends JPanel {

    protected int userId = -1;
    protected StringListener stringListener;
    protected final List<UserPanel> userPanelList;
    protected int width;
    protected int height;
    protected final Supplier<? extends T> ctor;

    public UsersViewPanel(int width, Supplier<? extends T> ctor){
        this.ctor  = Objects.requireNonNull(ctor);
        //
        userPanelList = new LinkedList<>();
        //
        this.width = width;
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
    }


    public void setUserPanels(List<String[]> info) throws IOException {
        userPanelList.clear();
        int height = 0;
        for (int i = 0 ; i < info.size() ; i++) {
             T userPanel = ctor.get();
             userPanel.setPanel(height, width);
             userPanel.addMouseListener(userPanel);
             try {
                 userPanel.setUserId(Integer.parseInt(info.get(i)[0]));
             }
             catch (Exception e){
                 userPanel.setName(info.get(i)[0]);
             }
             if(userId == userPanel.getUserId()){
                 userPanel.setClicked(true);
             }
             userPanel.setUsername(info.get(i)[1], Boolean.parseBoolean(info.get(i)[3]));
             userPanel.setProfileImage
                     (new ImageIcon(ImageIO.read(new File(info.get(i)[2]))));
             userPanelList.add(userPanel);
             addStringListenerToUserPanel(userPanel);
             height += 73;
        }
        this.height = height;
        this.setBounds(0,0,width, height);
    }

    public int getPreferredHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSize(){
        this.setPreferredSize(new Dimension(width,height));
    }

    public void addPanels(){
        removeAll();
        for (UserPanel userPanel: userPanelList) {
            this.add(userPanel);
        }
        repaint();
        revalidate();
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
        try {

            userId = Integer.parseInt(name);
        }
        catch (Exception ignored){

        }
        stringListener.stringEventOccurred(name);
    }

    public void addStringListenerToUserPanel(UserPanel userPanel){
        userPanel.setStringListener(this::listenMe);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<UserPanel> getUserPanelList() {
        return userPanelList;
    }

    public boolean isAnyPanelChosen(){
        for (UserPanel userPanel:userPanelList) {
            if(userPanel.isClicked){
                return true;
            }
        }
        return false;
    }

    public List<Integer> selectedPanelIds(){
        List<Integer> ids = new LinkedList<>();
        for (UserPanel userPanel:userPanelList) {
            if(userPanel.isClicked){
                ids.add(userPanel.getUserId());
            }
        }
        return ids;
    }

    public List<String> selectedPanelNames(){
        List<String> names = new LinkedList<>();
        for (UserPanel userPanel:userPanelList) {
            if(userPanel.isClicked){
                names.add(userPanel.getName());
            }
        }
        return names;
    }

    public void resetSelectedPanels(){
        for (UserPanel userPanel:userPanelList) {
            if(userPanel.isClicked){
                userPanel.setClicked(false);
            }
        }
    }

    public void resetPanel(){
        userId = -1;
        userPanelList.clear();
        removeAll();
        repaint();
        revalidate();
    }
}