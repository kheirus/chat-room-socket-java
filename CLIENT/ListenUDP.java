
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
public class ListenUDP extends Thread{

String message;
boolean b=true;

DatagramSocket socketUDP;
DatagramPacket receverPacketUDP;
byte [] Byte=new byte[1024];
private static final String ENCODING = "US-ASCII";
    public ListenUDP( DatagramSocket socketUDP ) {
       this.socketUDP= socketUDP;
       receverPacketUDP=new DatagramPacket(Byte, Byte.length);
      }
    
    
    public void run(){
         try {
            socketUDP.receive(receverPacketUDP);
            while ((receverPacketUDP) != null & b){
                message=new String(receverPacketUDP.getData(), 0, receverPacketUDP.getLength(),ENCODING);
                
               System.out.println(message);
                
                
                socketUDP.receive(receverPacketUDP);
                
                
               
            }
            
            
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
    
    
    
    }
    
    public int toint (String s){
        
         if (estUnEntier(s)) {
           return Integer.parseInt(s);
       }else {
           return 0;
       }
        
    }
    public  boolean estUnEntier(String chaine) {
		try {
			Integer.parseInt(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
    
    
}
