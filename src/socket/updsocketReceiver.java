package socket;

import java.io.IOException;
import java.net.*;

public class updsocketReceiver {
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;

    public updsocketReceiver() {
        try {
            datagramSocket = new DatagramSocket(10087);
            byte[] res = new byte[100];
            InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getByName("DESKTOP-8LLNL7O");
                datagramPacket = new DatagramPacket(res,100,inetAddress,10087);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    // listen and print the message
    public void receive(){
        try {
            datagramSocket.receive(datagramPacket);
            System.out.println("recive from client "+datagramPacket.getAddress()+":"+datagramPacket.getPort()+"  the information is ");
            byte[] res = datagramPacket.getData();
            System.out.println(new String(res,0,res.length));


        } catch (IOException e) {
            e.printStackTrace();
        }

        datagramSocket.close();

    }

    public static void main(String[] args) {
        updsocketReceiver updsocketReceiver = new updsocketReceiver();
        updsocketReceiver.receive();
    }
}
