
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class ListenUDP extends Thread {
    MulticastSocket multicastSocket;
    DatagramPacket receverPacketUDP,sendPacketUDP;
    DatagramSocket ds;
    boolean b =true;
    public static  ArrayList historique;
    
    byte [] Byte=new byte[1024];
    private static final String ENCODING = "US-ASCII";
    
    public ListenUDP(MulticastSocket multi ) {
        this.multicastSocket= multi;
        historique= new ArrayList();
        
                
        
        receverPacketUDP=new DatagramPacket(Byte, Byte.length);
    }
    
     public void run(){
         
        String [] str;
        String [] str2;
        String word,message;
        
        try {
            
            while ( b){
                multicastSocket.receive(receverPacketUDP);
                if ((receverPacketUDP) != null){
                message=new String(receverPacketUDP.getData(), 0, receverPacketUDP.getLength(),ENCODING);
                    str = message.split(" ",-1);
		    
		    // if (str[0].equals("ALIVE")){
		    // 	String repAlive ="200 ALIVE";
		    // 	byte [] data = repAlive.getBytes();
		    // 	sendPacketUDP = new DatagramPacket(data,data.lenght,new ) //adresse ???
		    //  ds = new DatagramSocket();
		    //  ds.send(sendPacketUDP);
		    // }





                    if (str[0].equals("BROADCAST")){
                        historique.add(str[1]);
                    }else if (str[0].equals("HELLO")) {
                        Iterator it = historique.iterator();
                        str2=str[1].split(",",-1);
                    
         
                       while (it.hasNext()) {
                           
             
                       sendPacketUDP=new DatagramPacket(("100 NEWS date "+it.next()).getBytes(), ("100 NEWS date "+it.next()).getBytes().length, InetAddress.getByName(str2[1]),Integer.parseInt(str2[2]));
		       ds = new DatagramSocket();
		       ds.send(sendPacketUDP);
         }
                    }
                }
            }
            
            
            
            
        } catch (IOException ex) {
            
        }
    
    
    
    
    }
    
}
