
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Datas {
  public static Map<String, String> connectedCafe;
  public static  ArrayList connectedCafeName;
  String []iPMulticaste = new String[5] ;
  
  public static int nbrCafe,maxNbrCafe=15;

    public Datas() {
        
        connectedCafe = new HashMap<String, String>();
        connectedCafeName =new ArrayList();
        iPMulticaste[0]= ("225.1.2.4");
        iPMulticaste[1]= ("225.1.2.5");
        iPMulticaste[2]= ("225.1.2.6");
        iPMulticaste[3]= ("225.1.2.7");
        iPMulticaste[4]= ("225.1.2.8");
        nbrCafe=0;
        
        }
   public boolean exist (String name){
       if (connectedCafeName.isEmpty()){
           return false ;
       }else if (connectedCafeName.contains(name)){
           return true;
       }else{
           return false;
       }
   }
    public void add(String str){
       connectedCafeName.add(str);
       
   }
     public void put(String str,String str2){
       connectedCafe.put(str, str2);
       
   }
     public boolean full (){
       if (nbrCafe <= maxNbrCafe){
           return false ;
       }else{
           return true;
       }
   }
     public String CafeNames (){
         Iterator it = connectedCafeName.iterator();
         String word =Integer.toString(connectedCafeName.size());
         word = word+" "+ it.next();
         while (it.hasNext()) {
             word = word+","+it.next();
         }
         return word;
             
   }
    public String getInfoCafe (String key){
        return connectedCafe.get(key);
        
        
        
    } 
    
    
    
    
    
}
