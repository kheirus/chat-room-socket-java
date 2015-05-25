
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
public class EcouterTCP extends Thread{
    Datas datas;
    Socket cliensock;
    PrintWriter writer;
    BufferedReader reader;
    String message;
private static final String ENCODING = "US-ASCII";
    public EcouterTCP(Socket clientSocket,Datas datas) {
        this.datas=datas;
        this.cliensock=clientSocket;
        try {
             writer = new PrintWriter(clientSocket.getOutputStream());
             InputStreamReader isReader = new InputStreamReader(cliensock.getInputStream());
				reader = new BufferedReader(isReader);
             
        } catch (IOException ex) {
            Logger.getLogger(EcouterTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void run(){
        String [] str;
        String [] str2;
        String word;
        try {
            while ((message = new String (reader.readLine().getBytes(),ENCODING)) != null) {
                System.out.println(message);
                str = message.split(" ",-1);
                if(str[0].equals("SHOPLIST")){
                    word = new String(("200 SHOPLIST "+datas.CafeNames()).getBytes(),ENCODING);
                    System.out.println(word);
                            
                    writer.println(word);
                    writer.flush();
                }else if (str[0].equals("SHOPINFO")){
                    if (datas.exist(str[1])){
                        word=new String (("200 SHOPINFO "+str[1]+","+ datas.getInfoCafe(str[1])).getBytes(),ENCODING);
                        writer.println(word);
                        writer.flush();
                    }else{
                        word=new String(("403 SHOPINFO not found").getBytes(),ENCODING);
                        System.out.println(word);
                        writer.println(word);
                        writer.flush();
                    } 
                }
                
                
                
                
                
            }
        } catch (IOException ex) {
	    ex.printStackTrace();
	    Logger.getLogger(EcouterTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }
    
    
    
}
