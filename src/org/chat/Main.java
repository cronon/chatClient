package org.chat;
import java.net.*;
import java.io.*;
import java.util.Random;
public class Main {
    static Random gen = new Random();
    static String randomNick(){
        return String.valueOf(gen.nextInt());
    }
    static BufferedReader in;
    public static void main(String[] args) {
        String nick = randomNick();
        if(args.length >= 1){
            nick = args[0];
        }
        String host = "localhost";
        if(args.length >= 2){
            host = args[0];
        }
        int port = 7777;
        if(args.length >= 3){
            port = Integer.parseInt(args[2]);
        }
        try (
                Socket socket = new Socket(host, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.print("xxx");
            (new ListenFromServer()).start();
            System.out.print("xxx");
            String userInput;
            String serverMessage;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                if(userInput.equals("close")){
                    socket.close();
                    System.exit(0);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    host);
            System.exit(1);
        }
    }
    static class ListenFromServer extends Thread {

        public void run() {
            while(true) {
                try {
                    String msg = in.readLine();
                    System.out.println(msg);
                }
                catch(IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}