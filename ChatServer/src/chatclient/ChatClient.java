/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;



/**
 *
 * @author NvHuy
 */
public class ChatClient extends Thread{
    private static DataOutputStream dos;
    private static DataInputStream dis;
    String tmp;
    
    public static void main(String arg[])
	{
            new ChatClient().start();
	}
    public void start(){
        try {
            Socket client = new Socket("localhost", 3200);
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            System.exit(0);
        }
    }
    //check login
    public String CheckLogin(String username, String password) 
    {
        start();
        try{
            sendMess("1");
            sendMess(username);
            sendMess(password);
            String response = dis.readUTF();
            return response;
        }catch(IOException ex)
        {
            ex.printStackTrace();
            return "Network error: Log In failed";
        }
    }
    //check sign up
    public String checkRegister(String username, String password)
    {
        start();
        try{
            sendMess("2");
            sendMess(username);
            sendMess(password);
            String respone = dis.readUTF();
            return respone;
        }catch(IOException ex){
            ex.printStackTrace();
            return  "Network error: Sign up failed";
        }
    }
    //send list client online
    public String getOnline()
    {
        try{
            String username=dis.readUTF();
            return username;
        }catch(IOException ex){
            ex.printStackTrace();
            return "Error";
        }
    }
    public String getChoose() throws IOException
    {
        tmp = dis.readUTF();
        return tmp;
    }
    private void sendMess(String data) {
        try {
            dos.writeUTF(data);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
}
