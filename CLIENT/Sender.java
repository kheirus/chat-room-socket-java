
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 *
 * @author Mohamed
 */
public class Sender extends Thread{
   // Datas datas;
    boolean b=true;
    String message,ip;
    MulticastSocket s;
    DatagramPacket sendPacketUDP;
    Scanner sc;
    int port;
    
    
    public Sender(MulticastSocket s,String ip , int port) {
      this.s=s;
      sc = new Scanner(System.in);
      this.ip=ip;
      this.port = port;
        
        
        
        
    }
    public void run(){
        
       
            while (b) {
            try {
                message ="MESSAGE "+sc.nextLine();
                
                sendPacketUDP=new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getByName(ip),port);
            try {                
                s.send(sendPacketUDP);
            } catch (IOException ex) {
                ex.printStackTrace();
                
            }
                
            } catch (UnknownHostException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                
            }
                
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
