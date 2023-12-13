package com.example;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(3322);
            
            while(true){
                Socket connection = s.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                DataOutputStream scrivi = new DataOutputStream(connection.getOutputStream());
                String parole;
                String stringa1 = null;
                String stringa2 = null;
                String stringa3 = null;
                parole = br.readLine();
                System.out.println(parole);
                String[] splitted = parole.split(" ");
                stringa1 = splitted[0];
                stringa2 = splitted[1];
                stringa3 = splitted[2];

                do {
                    String temp = br.readLine();
                    System.out.println(temp);
                } while (!br.readLine().isEmpty());
                
                
                File file = new File(stringa2.substring(1));
                if (file.exists()) {
                    System.out.println("il file esiste");
                    String msg ="il file esiste";
                    scrivi.writeBytes("HTTP/1.1 200 ok\n");
                    scrivi.writeBytes("Content_lenght: "+ msg.length()+"\n");
                    scrivi.writeBytes("\n");
                    scrivi.writeBytes(msg+"\n");

                } else {
                    System.out.println("il file non esiste");
                    String msg = "il file non esiste";
                    scrivi.writeBytes("HTTP/1.1 404 not found\n");
                    scrivi.writeBytes("Content_lenght: "+ msg.length()+"\n");
                    scrivi.writeBytes("\n");
                    scrivi.writeBytes(msg+"\n");

                }
                connection.close();
                
            }
            
        }

        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
        private static void SendBinaryFile(Socket socket, File file){
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.writeBytes("HTTP/1.1 200 OK\n");
            output.writeBytes("Content-lenght: "+ file.length()+"\n");
            output.writeBytes("content-type: "+getContentType(file)+"\n");
            output.writeBytes("\n");
            InputStream input = new FileInputStream(file);
            byte[] buf = new byte[8192];
            int n;
            while((n =input.read(buf)) !=-1){
                output.write(buf, 0, n);
            }
            input.close();
        }
    
}
