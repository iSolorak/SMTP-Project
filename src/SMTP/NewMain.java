/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SMTP;

/**
 *
 * @author Solorak
 */
public class NewMain {
  

   public static void main(final String[] args) {
      Thread t1 = new Thread(new Runnable() {
         public void run() {
            Authenticate_server.main(args);
         }
      });
      t1.start();
   }

   }  // 
    
 class Makis{
      public static void main(final String[] args) {
     Thread t2 = new Thread(new Runnable() {
         public void run() {
            Client.main(args);
         }
      });
     NewMain a = new NewMain();
     t2.start();
      }
    
}
