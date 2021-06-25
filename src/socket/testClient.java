package socket;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class testClient {
    public static void main(String[] args) throws IOException {
//        udpsocketSender sender = new udpsocketSender();
//        BufferedInputStream inputStream = new BufferedInputStream(System.in);
//        byte[] b = new byte[1000];
//        int len = -1;
//        while((len=inputStream.read(b))!=-1){  // 一直尝试读
//            sender.send(new String(b,0,len));
//        }
        String ss = "F:\\PyProject\\fightplane\\" + "a.txt";
        File f = new File("F:/PyProject/fightplane/a.txt");
        boolean is = f.exists();

        Socket s = new Socket("192.168.1.8",5050);
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        byte[] bb = new String("hello").getBytes();
        out.write(bb);
        out.flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.close();
        s.close();

    }
}
