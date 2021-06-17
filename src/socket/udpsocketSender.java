package socket;

import java.io.IOException;
import java.net.*;

public class udpsocketSender {
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;

    public udpsocketSender() {
        try {
            this.datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName("DESKTOP-8LLNL7O");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        byte[] buf = new byte[50];
        buf[0] = 30;
        this.datagramPacket = new DatagramPacket(buf,50, inetAddress, 10087);
    }
    // send
    public void  send(String message){
        try {
            datagramPacket.setData(message.getBytes());
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        datagramSocket.close();
    }

    public static void main(String[] args) {
        udpsocketSender sender = new udpsocketSender();
        sender.send("hello121212");
    }
}
