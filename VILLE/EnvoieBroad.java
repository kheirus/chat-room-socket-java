
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class EnvoieBroad extends Thread{
    DatagramSocket ds;
    DatagramPacket dp;
    
    EnvoieBroad (){
	
    }
    



	public void run(){
	try {
	    System.out.println("entrez le message du broadcast :");
	    Scanner sc = new Scanner(System.in);
	    String s = sc.next() ;
	    String bro = new String("BROADCAST "+s);
	    ds = new DatagramSocket();
	    dp = new DatagramPacket(bro.getBytes(),bro.getBytes().length,new InetSocketAddress("255.255.255.255",56556));
	    ds.send(dp);
	} catch(Exception e) {
	    e.printStackTrace();
    }
    }


}