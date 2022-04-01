package SMTP;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList; // import the ArrayList class


public class PassFileRead {
    static String file_path = "C:\\Users\\Solorak\\Documents\\NetBeansProjects\\2019_SOLUTION_SMTP_client_server\\";
    static String file_name = "passfile.txt";
    String where_to_read = file_path + file_name;

    public void FromFile(){
        try {
            File myObj = new File(where_to_read);
            Scanner myReader = new Scanner(myObj);
            System.out.println("List of registered users");
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
               // System.out.println(data);
            }
            myReader.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } 
   }     

    public String HandleData(String dataIn, String usernameIn){
        int point = dataIn.indexOf(" ");      //sepparate uname from passw
        String uname = dataIn.subSequence(0, point).toString();
        String upass = dataIn.subSequence(point+1,dataIn.length()).toString();
        System.out.println(uname+" "+upass);
        if(uname.equals(usernameIn)) //compare found with given name
        {
            System.out.println("found "+ usernameIn);// ERR HANDLING  
            Authenticate_server m = new Authenticate_server();
            //return upass; SALTED AND HASHED
            SaltpassHash sph = new SaltpassHash(upass);
            //System.out.println("12 "+sph.start());// ERR HANDLING  
            System.out.print(sph.start()+"THIS IS THE SPH");
            return sph.start();  //PLAIN upass;
        }
        else {
            System.out.println("found not "+usernameIn);// ERR HANDLING 
            return "";
       }        
    }
    
    
     public String SearchFile(String username){
        String result=null;

        System.out.println("searching "+ username);
        try {
            File myObj = new File(where_to_read);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();  //read lines from file
                result = HandleData(data, username);         // dreated class because method .hasNextLine locked at first line
               
                   
                 if(result!="" && result!=null  )
                {
                    return result;
                }
            }
            myReader.close();              
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } 
        return result;
    }
        
    
}
