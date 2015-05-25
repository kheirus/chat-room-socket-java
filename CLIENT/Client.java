
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
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
 * @author user
 */
public class Client {
    
    String ENCODING = "US-ASCII";
            
   
    String [] str,str2;
    String word;
    Socket cliensock;
    MulticastSocket multicastSocket;
    DatagramSocket socketUDP;
    DatagramPacket receverPacketUDP,sendPacketUDP;
    PrintWriter writer;
    BufferedReader reader;
    String message,reponse ,nomCafe,nomClient, ipVille,ipMultiCaste;
    int port,portVille,portMulticaste;
    byte [] Byte=new byte[1024];
    Scanner sc;
    
    boolean nomCaffeDejasConnais;
    public Client() {
        
	
	//port = 15153;
        reponse = "";
        sc = new Scanner(System.in);
        System.out.print("Nom Client :  ");
        nomClient  = sc.nextLine();
        System.out.println();
        System.out.print("Ip Ville : ");
        
       
         ipVille = sc.nextLine();
         System.out.println();
         System.out.print("port Ville : ");
         
         portVille= sc.nextInt();
         sc.nextLine();
         System.out.println();
        try {
	     cliensock =null;
	    int port = 0;
	    for (port=49152; cliensock==null && port<65536; port++) {
		try{
		cliensock = new Socket (InetAddress.getByName(ipVille),portVille);	    
		    break;
		}catch (Exception e){ System.out.println("tous les ports sont pris");}
            } 
             writer = new PrintWriter(cliensock.getOutputStream());
             
             InputStreamReader isReader = new InputStreamReader(cliensock.getInputStream());
            reader = new BufferedReader(isReader);
            receverPacketUDP=new DatagramPacket(Byte, Byte.length);
            VilleConnection();
            cafeConnection();
            
             
            
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	// } catch (Exception ex) {
	//     ex.printStackTrace();
	//     System.out.println("port non trouvé");
	// }
        
    }
    
    
    
    
    public void VilleConnection(){
        System.out.println("Connaissez vous  le nom du cafe O/N ");
                    
                reponse  = sc.nextLine();
            try {    
                 if (reponse.equals("O")){
                      System.out.println("C'est quoi le Nom ? ");
                      //recupurer le nom de caff
                        nomCafe  = sc.nextLine();
                       
                 
           
                
            
                word=new String (("SHOPINFO "+nomCafe).getBytes(),ENCODING);
                writer.println(word);
                writer.flush();
                }else{
                       word=new String (("SHOPLIST").getBytes(),ENCODING);
                       writer.println(word);
                       writer.flush();
                }
                 while (true){
                    try {
                        if(  (message = new String (reader.readLine().getBytes(),ENCODING)) != null){
                           str = message.split(" ",-1);
                           if(toint(str[0])== 200){
                            if(str[1].equals("SHOPLIST")){
                                if (toint(str[2])==0){
                                    System.out.println("Il y a pas des cafes connetés");
                                    break;
                                }else{
                                    str2=str[3].split(",", -1);
                                    //afficher les noms de cafes connectés 
                                    System.out.println("Cafés connectés");
                                    for (int j=0;j<str2.length;j++){
                                        System.out.println(str2[j]);
                                        
                                    } 
                                    //apres il faut choisir un cafe par l'utilisateur pour lancer la connexion
                                    System.out.println("Veuillez choisir un cafe");
                                      nomCafe= sc.nextLine();
                                        
                                    word=new String (("SHOPINFO "+nomCafe).getBytes(),ENCODING);
                                    writer.println(word);
                                    writer.flush();
                                    
                                }
                            }else if(str[1].equals("SHOPINFO")){
                                
                                //Lancer la connexion avec le cafe 
                                str2=str[2].split(",", -1);
                                System.out.println(toint(str2[2]));
                                System.out.println(str2[1]);
                                portMulticaste =toint(str2[2]);
                                ipMultiCaste=str2[1];
                                multicastSocket = new MulticastSocket(portMulticaste);
                                multicastSocket.joinGroup(InetAddress.getByName(ipMultiCaste));
                                System.out.println("                          Vous etes connecté ");
                                System.out.println();
                                System.out.println();
                                break;
                            }
                           }else if(toint(str[0])== 403){
                                System.out.println("Il y a pas des cafes connetés avec ce nom");
                                System.out.println("Veuillez entrer un nom valide");
                            
                                nomCafe= sc.nextLine();
                                    
                                word=new String (("SHOPINFO "+nomCafe).getBytes(),ENCODING);
                                writer.println(word);
                                writer.flush();
                        }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
               
            
            }
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
    
    
    
    }
    
    public void cafeConnection(){
        try {
             socketUDP = new DatagramSocket(this.port);
             message ="HELLO "+"nomclient"+","+"ip"+","+ Integer.toString(this.port);
             Byte = message.getBytes(ENCODING);
            try {
                sendPacketUDP=new DatagramPacket(Byte, Byte.length, InetAddress.getByName(str2[1]),toint(str2[2]));
            } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                multicastSocket.send(sendPacketUDP);
                ListenUDP listener = new ListenUDP(socketUDP);
		listener.start();
                Sender sender = new Sender(multicastSocket,ipMultiCaste,portMulticaste);
		sender.start();
                
                int j =0;
                
                multicastSocket.receive(receverPacketUDP); 
                while (receverPacketUDP != null){
                    message=new String(receverPacketUDP.getData(), 0, receverPacketUDP.getLength(),ENCODING);
                    str = message.split(message, -1);
                    System.out.println(message);
                    if (str[0].equals("MESSAGE")){
                        System.out.println(str[1] +": "+str[2]);
                    
                    }else if (str[0].equals("HEY")){
                        if(str[3].equals(nomClient)){
                               System.out.println(str[1]+"veut vous parler en chat privé O/N " );
                               
                               /*
                                if (Ok){
                                   
                                * 
                                * }
                                
                                
                                */
                               
                            }
                
                }else if (str[0].equals("CLOSE")){
                     listener.b=false;
                     sender.b= false;
                     cliensock.close();
                     multicastSocket.close();
                     socketUDP.close();

                     writer.close();
                    reader.close();
                    
                    break;
                
                
                }
                    multicastSocket.receive(receverPacketUDP); 
                }
                
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
             
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (SocketException ex) {
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

    public String getNomClient(){
	return this.nomClient;
    }




     public static void main (String[] args) {
         Client client = new Client();
     
     
     }
    
}
