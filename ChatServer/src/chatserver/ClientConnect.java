/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author NvHuy
 */
public class ClientConnect extends Thread{
    public Socket client;
    public Server server;
    private String username;
    private String password;
    private boolean run;
    private DataInputStream dis;
    private DataOutputStream dos;
    Hashtable<String,String> listClientAccount = new Hashtable<>();
    
    
    public ClientConnect(Server server, Socket client, Hashtable<String,String> listUserAccount ) {
        try {
            this.server = server;
            this.client = client;
            listClientAccount=listUserAccount;
            dos= new DataOutputStream(client.getOutputStream());
            dis= new DataInputStream(client.getInputStream());
            run = true;
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        //xử lý đăng nhập
        String msg=null;
        String choose = getMess();
        if(choose.equalsIgnoreCase("1"))
        {
            int check = checkLogin();
            if(check==1)
            {
                sendMess("1");
                while (run) {
                    server.status.append(username + " đã kết nối đến server\n");
                    //server.listClientConnected.put(username, this);
                    while (run) {
                        sendAllUpdate(username);
                        diplayAllClient();
                        while (run) {
                            int stt = Integer.parseInt(getMess());
                            switch (stt) {
                                case 0:
                                    run = false;
                                    server.listClientConnected.remove(this.username);
                                    exit();
                                    break;
                                case 1:
                                    msg = getMess();
                                    server.sendAll(username, username + " : " + msg);
                                    break;
                            }
                        }
                    }
                }
            }
            else if (check == 0) {
                sendMess("Sai mật khẩu!");
            } else {
                sendMess("Tài khoản không tồn tại");
            }
        }
        else if(choose.equalsIgnoreCase("2"))
        {
            int check = checkRegister();
            if(check == 0 ){
                sendMess("0");
            }
            else{
                sendMess("1");
            }
        }
        
    }


    public void sendAllUpdate(String username) {
        Enumeration e = listClientAccount.keys();
        String name = null;
        while (e.hasMoreElements()) {
            name = (String) e.nextElement();
            if (name.compareTo(username) != 0) {
                sendMess(name);
            }
        }
    }

    private void sendMess(String data) {
        try {
            dos.writeUTF(data);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMess(String msg1, String msg2) {
        sendMess(msg1);
        sendMess(msg2);
    }

    private String getMess() {
        String data = null;
        try {
            data = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void diplayAllClient() {
        String name = server.getAllName();
        sendMess("4");
        sendMess(name);
    }
        private void logout() {
        try {
            dos.close();
            dis.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exit() {
        try {
            //server.sendAllUpdate(username);
            dos.close();
            dis.close();
            client.close();
            server.status.append(username + " đã thoát\n");
            server.sendAll(username, username + " đã thoát\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //check login
    private int checkLogin() {
        username = getMess();
        password = getMess();
        if (listClientAccount.containsKey(username)) {
            if (listClientAccount.get(username).equalsIgnoreCase(password)) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 2;
        }
    }
    //check sign up
    private int checkRegister(){
        username = getMess();
        password = getMess();
        if(listClientAccount.containsKey(username))
        {
            return 0;
        }
        else{
            listClientAccount.put(username, password);
            server.listUserAccount.put(username, password);
            return 1;
        }
    }
}
