package SMTP;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


public class Client {
    //Main Method:- called when running the class file.
    public static void main(String[] args){ 
        
        int portNumber = 5000;
        String serverIP = "localhost";   
        
        try{
        //Create a new socket for communication
            Socket soc = new Socket(serverIP,portNumber);
        // use a semaphpre for thread synchronisation
        // AtomicBoolen() can synchronise the value of the variable among threads
            AtomicBoolean isDATA = new AtomicBoolean(false);
        // create new instance of the client writer thread, intialise it and start it running
            ClientReader client_read = new ClientReader(soc, isDATA);
            Thread clientReadThread = new Thread(client_read);
        //AG++++++++++Thread.start() is required to actually create a new thread 
        //so that the runnable's run method is executed in parallel.
        //The difference is that Thread.start() starts a thread that calls the run() method,
        //while Runnable.run() just calls the run() method on the current thread
            clientReadThread.start();
            
        // create new instance of the client writer thread, intialise it and start it running
            ClientWriter client_write = new ClientWriter(soc, isDATA);
            Thread clientWriteThread = new Thread(client_write);
            clientWriteThread.start();
        }
        catch (Exception except){
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error in SMTP_Client --> " + except.getMessage());
        }
    }
}



//This thread is responcible for writing messages
class ClientReader implements Runnable
{
    public static String ClientDomainName = "MyTestDomain.gr";
    public static String CRLF = "\r\n";
    
    Socket crSocket = null;
    AtomicBoolean isDATAflag;
    String BYTESin= "";
    String sDataToServer;
    
    public ClientReader (Socket inputSoc, AtomicBoolean isDATA){
        crSocket = inputSoc;
        this.isDATAflag = isDATA;
    }
  
    public void run(){
        // method 'isDATAflag.get()' returns the current value of the smaphore
        while(!crSocket.isClosed() && !isDATAflag.get()){
        // while connection is open and NOT IN DATA exchange STATE
            try
            {
                DataInputStream dataIn = new DataInputStream(crSocket.getInputStream());
                BYTESin = dataIn.readUTF();
                System.out.println(BYTESin);
                if (BYTESin.contains("221"))  
                {
                    System.out.println("Gracefully closing socket - PART 2/2");
                    crSocket.close();
                    return;
                }  
                else if (BYTESin.contains("250"))  
                {
                    System.out.println("OK -> CLIENT going to state SUCCESS");
                }   
                else if (BYTESin.contains("500"))  
                    System.out.println("SERVER Error--> Syntax error, command unrecognized");
                else if (BYTESin.contains("501"))  
                    System.out.println("SERVER Error--> Syntax error in parameters or arguments");        
                else if (BYTESin.contains("504"))  
                    System.out.println("SERVER Error--> Command parameter not implemented");
                else if (BYTESin.contains("421"))  
                    System.out.println("SERVER Error-->Service not available, closing transmission channel");
                else if (BYTESin.contains("354"))
                {
                    System.out.println("OK -> CLIENT going to state I (wait for data)");
                    isDATAflag.set(true);
                }
                else if (BYTESin.contains("334"))
                {
                    System.out.println("OK -> CLIENT autehenticating");
                    isDATAflag.set(true);
                }
            }  
            catch (Exception except){
              //Exception thrown (except) when something went wrong, pushing message to the console
              System.out.println("Error in ClientReader --> " + except.getMessage());
            }
        }
    }
}


class ClientWriter implements Runnable
{
    public static String CRLF = "\r\n";
    public static String ClientDomainName = "MyTestDomain.gr";
    public static String ClientEmailAddress = "myEmail@"+ClientDomainName;
    
    Socket cwSocket = null;
    AtomicBoolean isDATAflag;
    
    public ClientWriter (Socket outputSoc, AtomicBoolean isDATA){
        cwSocket = outputSoc;
        this.isDATAflag=isDATA;
    }
    
    public void run(){
        // initialise variables' value
        String msgToServer ="";
        String BYTESin= "";
    
        try{
            System.out.println ("CLIENT WRITER: SELECT NUMBER CORRESPONDING TO SMTP COMMAND 1. HELO _"
                    + "2. ATH,3, SEND Password,5 ,4 Send Mail,5. RCPT,6. Send data,7.Read User emails,8.QUIT");
            DataOutputStream dataOut = new DataOutputStream(cwSocket.getOutputStream());

            while (!cwSocket.isClosed()) {
                Scanner user_input = new Scanner(System.in);
                switch(user_input.nextInt()){
                    case 1: { // HELO
                        System.out.println("CLIENT WRITER SENDING HELO");
                        //
                        // SYNTAX (page 12 RFC 821)
                        // HELO <SP> <domain> <CRLF>
                        //
                        msgToServer = ("HELO"+" "+ClientDomainName+CRLF);
                        //String errorText500 = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
                        //sDataToServer = ("HELO"+" "+ClientDomainName+CRLF);  
                        dataOut.writeUTF(msgToServer);
                        // CODE EXPL // When you write data to a stream, it is not written immediately, and it is buffered. 
                        // CODE EXPL // So use flush() when you need your data from buffer to be written
                        dataOut.flush();                         
                        break;
                    }
                    case 2: { // AUTH
                        System.out.println("CLIENT WRITER SENDING AUTH");
                        //
                        // SYNTAX (page 8 RFC 4954)
                        //
                        msgToServer = ("AUTH"+" "+"PLAIN"+CRLF);
                        dataOut.writeUTF(msgToServer);
                        dataOut.flush();                         
                        break;
                    }
                    case 3: {
                        System.out.println("CLIENT WRITER SENDING PLAINpassword");
                        msgToServer = ("AlicePassw"+CRLF);
                        dataOut.writeUTF(msgToServer);
                        dataOut.flush();                         
                        break;
                    }                    

                    case 4: {
                        String mailDate="";
                                String mailFrom="" ;
                                String mailTo="";
                                        String mailSubject="";
                                        String mailBody="";
                          Scanner r = new Scanner(System.in);              
                        System.out.println("Enter Date");
                        mailDate = r.next();
                         System.out.println("Enter Sender");
                         mailFrom = r.next();
                        System.out.println("Enter RECEIVER");
                         mailTo = r.next();
                         System.out.println("Enter SUBJECT");
                          mailSubject= r.next();
                          System.out.println("Enter BODY");
                          mailBody = r.next();
                          
                          
                          
                       mailMessageContainer m = new mailMessageContainer(mailDate,  mailFrom, mailTo, mailSubject,mailBody);
                       m.run(mailDate, mailFrom, mailTo, mailSubject, mailBody);
                       //m.mailR(mailTo);
                       
                       
                    }                    
                    case 5: {
                        System.out.println("CLIENT WRITER SENDING RCPT");
                        //
                        // SYNTAX (page 28 RFC 821)
                        // RCPT <SP> TO:<forward-path> <CRLF>
                        //
                        String forwardPath = "alice@ServerDomain.gr";
                        msgToServer = ("RCPT"+" "+"TO:"+forwardPath+CRLF);
                        dataOut.writeUTF(msgToServer);
                        dataOut.flush();  
                      
                        break;
                    } 
                    case 6: {
                        System.out.println("CLIENT WRITER SENDING DATA");
                        //
                        // SYNTAX (page 5 and 28 RFC 821)
                        // DATA bla_bla_bla <CRLF>.<CRLF>
                        //
                        msgToServer = ("DATA"+CRLF);
                        dataOut.writeUTF(msgToServer);
                        dataOut.flush();                         
                        break;
                    }
                       case 7: {
                         String mailDate="";
                                String mailFrom="" ;
                                String mailTo="";
                                        String mailSubject="";
                                        String mailBody="";
                                           Scanner r = new Scanner(System.in); 
                                         System.out.println("Enter User to read mails");
                         mailTo = r.next();
                         mailMessageContainer m = new mailMessageContainer(mailDate,  mailFrom, mailTo, mailSubject,mailBody);
                          m.mailR(mailTo);  
                            System.out.println ("CLIENT WRITER: SELECT NUMBER CORRESPONDING TO SMTP COMMAND 1. HELO _"
                    + "2. ATH,3, SEND Password,5 ,4 Send Mail,5. RCPT,6. Send data,7.Read User emails,8.QUIT");
                        break;
                    }
                  
                    case 8: {
                        System.out.print("CLIENT : QUITing");
                        //
                        // SYNTAX (page 12 RFC 821)
                        // QUIT <CRLF>
                        //                        
                        msgToServer = ("QUIT"+CRLF);
                        dataOut.writeUTF(msgToServer);
                        dataOut.flush();                         
                        System.out.println("Gracefully closing socket - PART 1/2");
                        // NOTE:  IF STATED cwSocket.close(); THEN SOCKET WOULD BE CLOSED BEFORE SERVER RESPONSE!
                        return; // CODE EXPL // break WOULD NOT BE PROPER AS IT EXITS SWITCH BUT NOT THREAD
                        // NOTE: IF TERMINATING THREAD HERE, SERVER RESPONSE WOULD NOT BE READ AND GRACEFULL CLOSE WOULD NOT BE POSSIBLE
                    }//case 
                }    //switch     
            }        //while           
        }            //try
        catch (Exception except){
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error in ClientWriter --> " + except.getMessage());
        }
    }
}


