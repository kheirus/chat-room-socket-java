
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Scanner;
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
public class Villes {
    DatagramSocket socketUDP;
      int portUDP;
      ServerSocket serverSock ;
      int portTCP;
      Datas datas;
      
      
      Scanner sc;
    public Villes()  {
         datas=new Datas();
         sc = new Scanner(System.in);
        System.out.print("Port TCP:  ");
        this.portTCP  = sc.nextInt();
        sc.nextLine();
        System.out.println();
        System.out.print("Port UDP:  ");
         this.portUDP =sc.nextInt();
         sc.nextLine();
        
         // datas.add("CAFEa");
         // datas.add("CAFEb");
         // datas.add("CAFEc");
         
        try {
            try {
                serverSock   = new ServerSocket(portTCP);
                socketUDP = new DatagramSocket(portUDP);
                
            } catch (IOException ex) {
		
                Logger.getLogger(Villes.class.getName()).log(Level.SEVERE, null, ex);
            }
            socketUDP = new DatagramSocket(portUDP);
          } catch (SocketException ex) {
	    //ex.printStackTrace();   
	}
       
    }
    
    public void go (){
        
        try {
            System.out.println(portTCP);
           // serverSock = new ServerSocket(portTCP);
             LancerServerTCP listenerTCP = new LancerServerTCP(serverSock, datas);
             LancerServerUDP listenerUDP = new LancerServerUDP(socketUDP, datas);
	     //Thread broad = new Thread (new EnvoieBroad ());
             listenerUDP.start();
             listenerTCP.start();
	     // broad.start();
                                
            
        } catch (Exception ex) {
           System.err.println(ex);
         ex.printStackTrace();
         System.exit(1);
        }
        
        
        
        
    }
    
    
    
    
    
}
