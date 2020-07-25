/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

/**
 *
 * @author NvHuy
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame implements ActionListener{
	
	private JButton close;
	public JTextArea status;
	private ServerSocket server;
	public Hashtable<String, ClientConnect> listClientConnected;
	Hashtable<String,String> listUserAccount = new Hashtable<>();
        
	public Server(){
		setTitle("Máy chủ Server");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				try {
					//gởi tin nhắn tới tất cả client
					server.close();
					System.exit(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}	
		});
		setSize(450, 450);
		addUpdateStatus();
		setVisible(true);
                setResizable(false);
	}
	
	private void addUpdateStatus() {
		setLayout(new BorderLayout());
		
		add(new JLabel("Trạng thái server : \n"),BorderLayout.NORTH);
		add(new JPanel(),BorderLayout.EAST);
		add(new JPanel(),BorderLayout.WEST);
		
		status = new JTextArea(10,20);
		status.setEditable(false);
		add(new JScrollPane(status),BorderLayout.CENTER);
		
		close = new JButton("Close Server");
		close.addActionListener(this);
		add(close,BorderLayout.SOUTH);

		status.append("Máy chủ đã được mở.\n");
	}
	
	private void startServer(){
		try {
			server = new ServerSocket(3200);
			status.append("Máy chủ bắt đầu phục vụ\n");
			while(true){
				Socket client = server.accept();
                                listClientConnected = new Hashtable<String, ClientConnect>();
				new ClientConnect(this,client,listUserAccount);
			}
		} catch (IOException e) {
			status.append("Không thể khởi động máy chủ\n");
		}
	}
	
	public static void main(String[] args) {
		new Server().startServer();
	}

	public void actionPerformed(ActionEvent e) {
			try {
				server.close();
			} catch (IOException e1) {
				status.append("Không thể dừng được máy chủ\n");
			}
			System.exit(0);
	}
	public void sendAll(String username, String msg){
		Enumeration e = listClientConnected.keys();
		String name=null;
		while(e. hasMoreElements()){
			name=(String) e.nextElement();
			if(name.compareTo(username)!=0) listClientConnected.get(name).sendMess("3",msg);
		}
	}

	public String getAllName(){
		Enumeration e = listClientConnected.keys();
		String name="";
		while(e. hasMoreElements()){
			name+=(String) e.nextElement()+"\n";
		}
		return name;
	}
}