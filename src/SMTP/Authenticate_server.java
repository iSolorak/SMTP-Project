package SMTP;
  
import java.io.*;
import java.net.*;
import java.util.ArrayList; // import the ArrayList class
import java.util.Scanner;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors



public class Authenticate_server {
    private int currentTot;
    Socket client;
    socketManager clientSocObj;
    String bytesRead;

    public void start() throws IOException { 
        ServerSocket serverSoc = new ServerSocket(6000);
        System.out.println("Waiting for connection from client");
         //accept connection from client
        Socket client = serverSoc.accept();
        clientSocObj = new socketManager(client);        
        try {
            System.out.println("client connected at prot" + clientSocObj.soc.getPort());
            logInfo();
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void logInfo() throws Exception{
        String usercheck;
        String username = clientSocObj.input.readUTF();
        PassFileRead check_uname =new PassFileRead();       // use class PassFileRead to access AuthFile
        usercheck = check_uname.SearchFile(username); // use method in class PassFileRead to check
        // ERR HANDLING  System.out.println("123"+usercheck);
        reader(username);
        if(usercheck!="")
        {
            clientSocObj.output.writeUTF("334 AUTH PLAIN OK");        // send indo socket

            // check pasword
            String userpass = clientSocObj.input.readUTF();
            if (usercheck.equals(userpass)){
                clientSocObj.output.writeUTF("235 2.7.0 Authentication successful");    //page 7 RFC4954
                clientSocObj.output.flush();  //lempty output buffer
                clientSocObj.output.writeUTF("OK");    //page 7 RFC4954
                clientSocObj.output.flush();  //lempty output buffer                
                //client.close();
                 //  
                Server.main(null);
              
            
                
               
                //server.start();
                
            }
            else
            {
                clientSocObj.output.writeUTF("535 5.7.8p  Authentication credentials invalid");
                clientSocObj.output.flush();  //lempty output buffer
            }
        }
        else if (usercheck==null){
            System.out.print("Null Value");
            
        }
        
        
        else
        {
            clientSocObj.output.writeUTF("535 5.7.8u  Authentication credentials invalid");
            System.out.println("user not found");
        }
        clientSocObj.output.close();

    }

    public static void main(String[] args){
        Authenticate_server server = new Authenticate_server();
        try {
            server.start();
               
          
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        
        
        
    }  
     public  void logger(String username) throws IOException{
         
         try{
       FileWriter myWriter = new FileWriter("C:\\Users\\Solorak\\Documents\\NetBeansProjects\\2019_SOLUTION_SMTP_client_server\\log.txt");
      myWriter.write("This user logged in "+username);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
      
         }
         catch(IOException e){
             System.out.print("Logger Error "+" "+e);
         }
           
       }
     
      public  void reader(String username) throws IOException{
         
         try{
        File myObj = new File("C:\\Users\\Solorak\\Documents\\NetBeansProjects\\2019_SOLUTION_SMTP_client_server\\log.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        if (data.contentEquals(username)){
            System.out.print("Already Logged in");
        }
        else {
            System.out.print(data);
            logger(username); //Server.main(null); //System.out.print(data);
        }
      }
      
         }
         catch(IOException e){
             System.out.print("Reader Error "+" "+e);
         }
           
       }
    
    
}

