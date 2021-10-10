package app.notification;

import events.notofication.NotifEvent;
import network.EventListener;
import resources.Colors;
import resources.Fonts;
import resources.Texts;
import responses.notification.NotifResponse;
import responses.visitors.ResponseVisitor;
import responses.visitors.notification.NotifResponseVisitor;
import view.notificationView.NotificationPanel;
import view.notificationView.RequestPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class NotificationMainPanel extends JPanel implements NotifResponseVisitor {

    private final JScrollPane downPanel;
    private final JPanel panel;
    private final EventListener eventListener;

    public NotificationMainPanel(EventListener eventListener,
                                 HashMap<String, ResponseVisitor> visitors) {
        this.eventListener = eventListener;
        visitors.put("NotifResponseVisitor", this);
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        //
        JButton myRequest = new JButton(Texts.MY_REQUEST);
        JButton otherRequest = new JButton(Texts.OTHER_REQUEST);
        JButton systemMessages = new JButton(Texts.SYSTEM_MESSAGES);
        makeButton(myRequest);
        myRequest.addActionListener(e-> {
            NotifEvent event = new NotifEvent();
            event.setEvent("loadMyReq");
            eventListener.listen(event);
        });
        makeButton(otherRequest);
        otherRequest.addActionListener(e->{
            NotifEvent event = new NotifEvent();
            event.setEvent("loadOtherReq");
            eventListener.listen(event);
        });
        makeButton(systemMessages);
        systemMessages.addActionListener(e->{
            NotifEvent event = new NotifEvent();
            event.setEvent("loadSystemMessages");
            eventListener.listen(event);
        });
        myRequest.setBounds(20,10,240,60);
        otherRequest.setBounds(270,10,240,60);
        systemMessages.setBounds(520,10,240,60);
        //
        downPanel = new JScrollPane(panel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        downPanel.setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
        downPanel.setBounds(0,80,800,620);
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.INFO_PANEL));
        this.setBounds(260,50,800,700);
        this.add(myRequest);
        this.add(otherRequest);
        this.add(systemMessages);
        this.add(downPanel);
    }

    public void getAnswer(NotifResponse response){
        if("loadOtherReq".equals(response.getAnswer())){
            setOtherRequests(response.getInfo());
        }
        else if("loadMyReq".equals(response.getAnswer())){
            setMyRequest(response.getInfo());
        }
        else if("loadSystemMessages".equals(response.getAnswer())){
            setMessages(response.getInfo());
        }
    }

    public void makeButton(JButton button){
        button.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        button.setFocusable(false);
        button.setFont(Fonts.BUTTONS_FONT);
    }

    public void setOtherRequests(List<String[]> info){
        panel.removeAll();
        List<RequestPanel> messages = new LinkedList<>();
        int height = 5;
        for (String[] string:info) {
            RequestPanel message = new RequestPanel();
            message.setInfo(height,string[0],Integer.parseInt(string[1]));
            setStringListenerForSubPanels(message);
            messages.add(message);
            height += 205;
        }
        panel.setPreferredSize(new Dimension(800, height));
        for (RequestPanel t:messages) {
            panel.add(t);
        }
        downPanel.setViewportView(panel);
        repaint();
        revalidate();
    }

    public void setMyRequest(List<String[]> info){
        panel.removeAll();
        List<NotificationPanel> messages = new LinkedList<>();
        int height = 5;
        for (String[] string:info) {
            NotificationPanel message = new NotificationPanel();
            message.setInfo(height,string[0],Integer.parseInt(string[1]));
            messages.add(message);
            height += 205;
        }
        panel.setPreferredSize(new Dimension(800, height));
        for (NotificationPanel t:messages) {
            panel.add(t);
        }
        downPanel.setViewportView(panel);
        repaint();
        revalidate();
    }

    public void setMessages(List<String[]> info){
        panel.removeAll();
        List<NotificationPanel> messages = new LinkedList<>();
        int height = 5;
        for (String[] string: info) {
            NotificationPanel message = new NotificationPanel();
            message.setInfo(height,string[0],Integer.parseInt(string[1]));
            messages.add(message);
            height += 205;
        }
        panel.setPreferredSize(new Dimension(800, height));
        for (NotificationPanel t: messages) {
            panel.add(t);
        }
        downPanel.setViewportView(panel);
        repaint();
        revalidate();
    }

    public void setStringListenerForSubPanels(RequestPanel requestPanel){
     requestPanel.setStringListener(text -> {
         NotifEvent event = new NotifEvent();
         event.setEvent(text.substring(0, 6));
         event.setReqId(Integer.parseInt(text.substring(6)));
         eventListener.listen(event);
         NotifEvent event1 = new NotifEvent();
         event1.setEvent("loadOtherReq");
         eventListener.listen(event1);
         repaint();
         revalidate();
     });
    }
}
