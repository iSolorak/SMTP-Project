package SMTP;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.io.IOException;
public class Server extends Thread {
    public static void main(String[] args){ 
        // initialise socket connection characteristics
        int portNumber = 5000;
        
        //execute server logic
        try{
        // create socket instance for incoming requests
            ServerSocket serverSoc = new ServerSocket(portNumber);
            ArrayList<socketManager> active_clients = new ArrayList<socketManager>();
        // perform WAIT state
        //Authenticate_server.main(null);
            System.out.println("SERVER IN WAIT STATE");
            while (true)
            {
                
        // accept incoming requests        
                Socket soc = serverSoc.accept();
                socketManager incoming_connection_request = new socketManager(soc);
                active_clients.add(incoming_connection_request);
        // create a server thread for handling each incoming client connection        
                ServerConnectionHandler sch = new ServerConnectionHandler(active_clients, incoming_connection_request);
                //System.out.println("AAAAAAAATEST");
                
               //Thread m new Authenticate_server(m);
                //Authenticate_server.main(null);
                 //Authenticate_server.main(null);
                Thread schThread = new Thread(sch);
                schThread.start();
                
                //server1.start();
        // check ServerConnectionHandler to see how server actualy opperates
            }
        }
        catch (Exception except){
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error Server --> " + except.getMessage());
        }
    }  
    
    
  
}
    
