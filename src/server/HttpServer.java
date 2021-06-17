package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) {
        int port;
        ServerSocket serverSocket;
        try{
            port =Integer.parseInt(args[0]);
        }catch (Exception e){
            port = 8080;
            System.out.println("server is using port "+ port);
        }

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("server is listen at port "+ port);

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("get connecting from clinet and address is : "+ socket.getInetAddress()+":"+socket.getPort());

                service(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void service(Socket socket){

    }
}
