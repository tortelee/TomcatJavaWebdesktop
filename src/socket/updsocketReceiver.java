package socket;

import java.io.IOException;
import java.net.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class updsocketReceiver {
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;

    public updsocketReceiver() {
        try {
            datagramSocket = new DatagramSocket(5001);
            byte[] res = new byte[100];
            InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getByName("DESKTOP-8LLNL7O");
                System.out.println("server start at    "+inetAddress.getHostAddress());
                datagramPacket = new DatagramPacket(res,100,inetAddress,5001);

                System.out.println("the socket is bound to  "+ datagramSocket.isBound()+ " "+ " "+datagramSocket.getLocalPort());

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
            long time = System.currentTimeMillis();
            Calendar c = Calendar.getInstance();
            Date d = c.getTime();
            System.out.println(d.toString()+" "+datagramPacket.getAddress()+":"+datagramPacket.getPort()+" say ");
            byte[] res = datagramPacket.getData();
            System.out.println(new String(res,0,datagramPacket.getLength()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        updsocketReceiver updsocketReceiver = new updsocketReceiver();
        while(true) {
            updsocketReceiver.receive();
        }
    }
}
