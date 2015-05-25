
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
public class LancerServerTCP extends Thread{
    ServerSocket serverSock;
    Socket clientSock ;
    Datas datas ;
    static int i=1;
    

    public LancerServerTCP(ServerSocket serverSock,Datas datas) {
        this.serverSock = serverSock;
        this.datas= datas;
        
    }
    
  public  void run (){
        try {
            
            while (true){
		
		clientSock = serverSock.accept();
		
		System.out.println("CLIENT "+i+" vient de se connecter");
		i++; 
             Thread listener = new Thread(new EcouterTCP(clientSock,datas));
				listener.start();
             
             
             
             /*
              * 
              * lancer EcouterTCP pour traiter les messages reçus par la connexion TCP
              * 
              
              */
               }
             
             
             
        } catch (IOException ex) {
	    ex.printStackTrace();
            Logger.getLogger(LancerServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    
    
    
    
    
}
