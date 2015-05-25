
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
public class LancerServerUDP extends Thread{

String message;
String ipMutiCaste;
Datas datas ;
DatagramSocket socketUDP;
DatagramPacket receverPacketUDP,sendPacketUDP;

byte [] Byte=new byte[1024];
private static final String ENCODING = "US-ASCII";
    public LancerServerUDP( DatagramSocket socketUDP , Datas datas) {
       this.socketUDP= socketUDP;
       receverPacketUDP=new DatagramPacket(Byte, Byte.length);
       this.datas = datas;
       
        
    
    }
    
    
    public void run(){
        String [] str;
        String [] str2;
        String word;
        
        try {
            socketUDP.receive(receverPacketUDP);
            while ((receverPacketUDP) != null){
                message=new String(receverPacketUDP.getData(), 0, receverPacketUDP.getLength(),ENCODING);
                str = message.split(" ",-1);
                if (str[0].equals("NEWSHOP")) {
                    str2 = str[1].split(",", -1);
                    if (datas.exist(str2[0])){
                        word = "404 NEWSHOP existing "+str2[0];
                        Byte = word.getBytes(ENCODING);
                        sendPacketUDP=new DatagramPacket(Byte, Byte.length, receverPacketUDP.getAddress(),receverPacketUDP.getPort());
                        socketUDP.send(sendPacketUDP);
                    }else if (datas.full()){
                          word ="500 NEWSHOP city full";
                          Byte = word.getBytes(ENCODING);
                          sendPacketUDP=new DatagramPacket(Byte, Byte.length, receverPacketUDP.getAddress(),receverPacketUDP.getPort());
                          socketUDP.send(sendPacketUDP);
                          
                    }else if (!datas.full()){
                        datas.add(str2[0]);
                        
                        
                        word = datas.iPMulticaste[Datas.nbrCafe]+","+str2[1];
                        
                        datas.put(str2[0],word);
                        
                        word = "200 NEWSHOP "+datas.iPMulticaste[Datas.nbrCafe];
                        Byte = word.getBytes(ENCODING);
                        sendPacketUDP=new DatagramPacket(Byte, Byte.length, receverPacketUDP.getAddress(),receverPacketUDP.getPort());
                        socketUDP.send(sendPacketUDP);
                        Datas.nbrCafe ++;
                    }
		   
                }

		else{
                    word = "501 SYNTAX";
                        Byte = word.getBytes(ENCODING);
                        sendPacketUDP=new DatagramPacket(Byte, Byte.length, receverPacketUDP.getAddress(),receverPacketUDP.getPort());
                        socketUDP.send(sendPacketUDP);
                }
                
		//envoie des messages broad cast

		
                
                socketUDP.receive(receverPacketUDP);
                
                
                
            }
            
             
            
            
        } catch (IOException ex) {
	    ex.printStackTrace();
            Logger.getLogger(LancerServerUDP.class.getName()).log(Level.SEVERE, null, ex);
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
