
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.



*/

/**
 *
 */
public class Cafe {
         DatagramSocket socketUDP;
         DatagramPacket sendPacketUDP,receverPacketUDP;
         MulticastSocket multicastSocket;
         String nomcafe,msg,ipVille;
         String [] str;
         String [] str2;
         boolean b=true;
         int  portVille ,portMultcast;
    byte [] Byte=new byte[1024];
    private static final String ENCODING = "US-ASCII";
    Scanner sc;
    public Cafe() {
       // this.port = port;
        sc = new Scanner(System.in);
         System.out.print("Ip Ville : ");
       
         ipVille = sc.nextLine();
         System.out.println();
        System.out.print("Port ville :  ");
        portVille  = sc.nextInt();
        sc.nextLine();
        System.out.println();
        System.out.print("Port multicaste :  ");
        portMultcast  = sc.nextInt();
        sc.nextLine();
        System.out.println("Nom cafe ");
        //recupurer le nom de caff
        nomcafe  = sc.nextLine();
        System.out.println();
        receverPacketUDP=new DatagramPacket(Byte, Byte.length);
        msgToVille();
       
        
    }
    public void msgToVille()
    { 
        try {            
            DatagramSocket socketUDP = new DatagramSocket();
            msg = "NEWSHOP "+nomcafe +","+ Integer.toString(portMultcast);
            Byte = msg.getBytes(ENCODING);
            sendPacketUDP=new DatagramPacket(Byte, Byte.length, InetAddress.getByName(ipVille),portVille);
            try {
                socketUDP.send(sendPacketUDP);
                while (b){
                socketUDP.receive(receverPacketUDP);
                if (receverPacketUDP != null){
                    msg=new String(receverPacketUDP.getData(), 0, receverPacketUDP.getLength(),ENCODING);
                    str = msg.split(" ",-1);
                    if (str[0].equals("200")){
                        if (str[1].equals("NEWSHOP")){
                        
                            //LANCER LE CAFE 
                            b=false;
                            socketUDP.close();
                            lancerCafe();
                            }
                    }else if (str[0].equals("404")){
                        if (str[1].equals("NEWSHOP")){
                             if (str[2].equals("existing")){
                            System.out.println("Ce nom est  deja existe  vuillez choisir un autre nom");
                            //recupurer le nom de caff
                            nomcafe  = sc.nextLine();
                            System.out.println();
                            msg = "NEWSHOP "+nomcafe +","+ Integer.toString(portMultcast);
                            Byte = msg.getBytes(ENCODING);
                            sendPacketUDP=new DatagramPacket(Byte, Byte.length, InetAddress.getByName(ipVille),portVille);
                            socketUDP.send(sendPacketUDP);
                        }
                        }
                    }else if (str[0].equals("500")){
                                System.out.println("Ville Complet ");
                                socketUDP.close();
                    }
                }
                
                
                }
                
                
                /*ListenUDP listener = new ListenUDP(socketUDP,ipVille,portVille);
                                    listener.start();*/
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
             ex.printStackTrace();
        } catch (SocketException ex) {
             ex.printStackTrace();
        }
            
        
    }
    public void lancerCafe(){
        try {
            multicastSocket = new MulticastSocket(portMultcast);
            multicastSocket.joinGroup(InetAddress.getByName(str[2]));
            ListenUDP listen = new ListenUDP(multicastSocket);
                listen.start();
                System.out.println ("Pour fermer le cafe entrez CLOSE ");
                sc.nextLine();
                sendPacketUDP=new DatagramPacket(("CLOSE").getBytes(), ("CLOSE").getBytes().length, multicastSocket.getInetAddress(),multicastSocket.getPort());
                multicastSocket.send(sendPacketUDP);
        
                multicastSocket.close();
                listen.b=false;
                
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
     public static void main (String[] args) {
         Cafe cafe = new Cafe();
     
     
     }
    
}
