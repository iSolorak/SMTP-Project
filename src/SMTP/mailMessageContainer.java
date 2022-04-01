package SMTP;
import java.io.*;
import java.net.*;
import java.util.ArrayList; // import the ArrayList class
import java.util.Scanner;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors


public class mailMessageContainer {

    private static void logger11(String test) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public String date_container;
    public String from_container;
    public String to_container;
    public String subject_container;
    public String mail_body_container;
    
    public mailMessageContainer(String mailDate, String mailFrom, String mailTo, String mailSubject, String mailBody){
        date_container = mailDate;
        from_container = mailFrom;
        to_container = mailTo;
        subject_container = mailSubject;
        mail_body_container = mailBody;
    }
    
    public  void run(String mailDate, String mailFrom, String mailTo, String mailSubject, String mailBody) throws IOException{
        
        logger132("\n",mailFrom);
        System.out.println(mailDate);
        logger132("MAIL DATE : "+mailDate,mailFrom);
        System.out.println(mailFrom);
        logger132("MAIL FROM : "+mailFrom,mailFrom);
        System.out.println(mailTo);        
        logger132("MAIL TO : "+mailTo,mailFrom);
        System.out.println(mailSubject);
        logger132("MAIL SUBJECT : "+mailSubject,mailFrom);
        System.out.println(mailBody);
        logger132("MAIL BODY : "+mailBody,mailFrom);
        
      
     
           
       }
     public static void logger132(String username,String whereW){
         
         try{
               FileOutputStream myWriter = new FileOutputStream("C:\\Users\\Solorak\\Documents\\NetBeansProjects\\2019_SOLUTION_SMTP_client_server\\"+whereW+".txt", true);
                String lineToAppend = username+"\n";
               byte[] t = lineToAppend.getBytes(); //converting string into byte array
      myWriter.write(t);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
      
         }
         catch(IOException e){
             System.out.print("Logger Error "+" "+e);
         }
         
         
     }
          public static void mailR(String username){
         
         try{
             File myObj = new File("C:\\Users\\Solorak\\Documents\\NetBeansProjects\\2019_SOLUTION_SMTP_client_server\\"+username+".txt");
            Scanner myReader = new Scanner(myObj);
                 while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
               System.out.println(data);
            }
            myReader.close();
      
         }
         catch(IOException e){
             System.out.print("Logger Error "+" "+e);
         }
          }
}
