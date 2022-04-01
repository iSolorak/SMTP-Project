package SMTP;

import java.io.File;
import java.nio.file.*;

/**
 *
 * @author gpik
 */
public class mailFileHandler {
    static String file_path = "C:\\Users\\Solorak\\Documents\\NetBeansProjects\\2019_SOLUTION_SMTP_client_server\\";
    static String file_name = ".txt";
    String mailUserName="";
    String where_to_write = file_path + file_name;
    
    public mailFileHandler(String uname, mailMessageContainer email){
        mailUserName = uname;
        
    }
    
    public void createMailBox(){
        boolean result = false;
        file_path = file_path + mailUserName;
        //System.out.println(file_path);
        File theDir = new File(file_path);
        System.out.println("THIS THE PATH TO MAIL "+mailUserName);
         System.out.println(theDir.exists()+" "+"IF EXISTS");
           System.out.println(theDir.exists()+" "+"IF EXISTS");
        if (theDir.exists()==false && theDir.getName()!=mailUserName) {
            System.out.println("creating directory: " + theDir.getName());
            result = false;
            try{
               
               // theDir.mkdir();
                result = true;
            } 
            catch(SecurityException se){
                //handle it
            }        
        }
        if(result) {    
            System.out.println("DIR created");  
    }
}
    
    
    
    public static void main(String[] args){ 
        mailMessageContainer test = new mailMessageContainer("1","2","3","4","5");
        mailFileHandler self = new mailFileHandler("Solorak", test);
        System.out.print(self);
            self.createMailBox();
    }
}
