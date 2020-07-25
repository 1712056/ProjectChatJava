/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 *
 * @author NvHuy
 */
public class ChatJFrame extends JFrame implements ActionListener{
    private JButton send;
    private JPanel chatPanel, userOnline;
    private JTextField nick,nick1,message;
    private JTextArea msg;
    private static DataInputStream dis;
    
    public ChatJFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550,400);
        addItem();
        setVisible(true);
    }

    private void addItem() {
        setLayout(new BorderLayout());
        send = new JButton("Gửi");
        send.addActionListener(this);

        chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        nick = new JTextField(20);
        nick.setEditable(false);
        p1.add(new JLabel("Username : "));
        p1.add(nick);
        

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());

        JPanel p22 = new JPanel();
        p22.setLayout(new FlowLayout(FlowLayout.CENTER));
        p22.add(new JLabel("Danh sách online"));
        p2.add(p22, BorderLayout.NORTH);

        userOnline = new JPanel();
        userOnline.add(new JButton());
        p2.add(new JScrollPane(userOnline), BorderLayout.CENTER);
        p2.add(new JLabel("  "), BorderLayout.SOUTH);
        p2.add(new JLabel("  "), BorderLayout.EAST);
        p2.add(new JLabel("  "), BorderLayout.WEST);

        msg = new JTextArea(10, 20);
        msg.setEditable(false);

        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Nội dung:"));
        message = new JTextField(30);
        p3.add(message);
        p3.add(send);

        chatPanel.add(new JScrollPane(msg), BorderLayout.CENTER);
        chatPanel.add(p1, BorderLayout.NORTH);
        chatPanel.add(p2, BorderLayout.EAST);
        chatPanel.add(p3, BorderLayout.SOUTH);
        chatPanel.add(new JLabel("     "), BorderLayout.WEST);
        add(chatPanel, BorderLayout.CENTER);
        //-------------------------
    }
    public void actionPerformed(ActionEvent e) {
        
    }
    public static void main(String[] args) {
	new ChatJFrame().start();
    }

    private void start() {
        ChatClient chatClient = new ChatClient();
        chatClient.getOnline();
    }

}
