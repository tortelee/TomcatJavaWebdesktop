package socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        this.datagramPacket = new DatagramPacket(buf,50, inetAddress, 5001);
    }
    // send
    public void  send(String message){
        try {
            byte[] mes = message.getBytes();
            datagramPacket.setData(mes);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        udpsocketSender sender = new udpsocketSender();
        BufferedInputStream inputStream = new BufferedInputStream(System.in);
        byte[] b = new byte[1000];
        int len = -1;
        while((len=inputStream.read(b))!=-1){  // 一直尝试读
            sender.send(new String(b,0,len));
        }

    }
}
