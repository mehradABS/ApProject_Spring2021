package view.postView.commentPage;

import resources.Colors;
import resources.Images;
import resources.Texts;
import view.postView.tweetHistory.PostView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class CommentView extends PostView {

    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenuItem blockItem;
    private final JMenuItem reportItem;
    private final JMenuItem muteItem;
    private final JMenuItem unblockItem;
    private final JMenuItem deleteItem;

    public CommentView() {
        super();
        menuBar = new JMenuBar();
        fileMenu = new JMenu(Texts.MENUBAR);
        fileMenu.setBackground(Color.decode(Colors.POST_VIEW));
        menuBar.add(fileMenu);
        menuBar.setBounds(620,10,50,40);
        menuBar.setBackground(Color.decode(Colors.POST_VIEW));
        blockItem = new JMenuItem(Images.BLOCK_ICON);
        reportItem = new JMenuItem(Images.REPORT_ICON);
        muteItem = new JMenuItem(Images.MUTE_ICON);
        blockItem.setBackground(Color.decode(Colors.BLOCK));
        unblockItem = new JMenuItem(Images.UNBLOCK_ICON);
        unblockItem.setBackground(Color.decode(Colors.BLOCK));
        reportItem.setBackground(Color.decode(Colors.BLOCK));
        muteItem.setBackground(Color.decode(Colors.BLOCK));
        deleteItem = new JMenuItem(Texts.DELETE);
        fileMenu.add(reportItem);
        fileMenu.add(muteItem);
        //
        //TODO add actionListener
        addActionListenersToItems(blockItem, "block");
        addActionListenersToItems(muteItem, "mute");
        addActionListenersToItems(reportItem, "report");
        addActionListenersToItems(unblockItem, "unblock");
        //
        this.add(menuBar);
    }

    public void setMoreIcon(boolean visible){
          menuBar.setVisible(visible);
          menuBarVisibility = visible;
          repaint();
          revalidate();

    }

    public boolean getMoreIconVisibility(){
        return menuBarVisibility;
    }

    public void setBlockItem(boolean active){
        if(active){
            fileMenu.add(unblockItem);
            fileMenu.remove(blockItem);
        }
        else{
            fileMenu.remove(unblockItem);
            fileMenu.add(blockItem);
        }
        fileMenu.repaint();
        fileMenu.revalidate();
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void setDeleteItem(boolean deletable){
        if(deletable){
            fileMenu.removeAll();
            fileMenu.add(deleteItem);
            addActionListenersToItems(deleteItem, "delete");
            menuBar.setVisible(true);
            repaint();
            revalidate();
        }
        else{
            menuBar.setVisible(false);
        }
    }

    public void addActionListenersToItems(JMenuItem menuItem ,String text){
        menuItem.addActionListener(e -> {
            try {
                listenMe(text);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}
