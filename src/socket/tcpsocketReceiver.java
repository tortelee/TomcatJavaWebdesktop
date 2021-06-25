package socket;





import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

// this is a server that could request file to client
// tcp -->socket --> http --> http --> socket

/**
 * 1， 监听端口
 * 2， 创建连接，将连接给下面处理
 */
public class tcpsocketReceiver {

    public int port;
    public int maxConnection;
    public InetAddress inetAddress;
    public ServerSocket serverSocket;

    // file folder in the server
    public String ROOT_DIR = "F:\\PyProject\\fightplane";


    public tcpsocketReceiver(int port, int maxConnection, String inetaddress) {
        this.port = port;
        this.maxConnection = maxConnection;
        try {
            this.inetAddress = InetAddress.getByName(inetaddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        try {
            if(inetaddress!=null) {
                this.serverSocket = new ServerSocket(port,maxConnection,inetAddress);
            }else {
                this.serverSocket = new ServerSocket(port, maxConnection);
            }

            System.out.println("server start at ip "+ serverSocket.getInetAddress().toString() + "  port is " + serverSocket.getLocalPort());

        } catch (IOException e) {
            System.out.println("create server socket failed "+e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean stop;
    public void runService(){
        while(true) {
            if (stop) {
                System.out.println("system is terminated");
                break;
            }


            try {
                System.out.println("Listening....");
                Socket socket = serverSocket.accept();  // 一直监听，然后返回一个连接对象；
                System.out.println("get connection from" + socket.getInetAddress().toString() + ":"+ socket.getPort());
                new Thread(new HanderRequest(socket,ROOT_DIR)).start(); // 去处理请求了


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

/**
 * 1, 处理的是http请求
 *
 * 2， 返回的也是http 响应
 */
class HanderRequest implements Runnable{
    public String ROOT_DIR;
    public Socket socket;

    public HanderRequest( Socket socket,String ROOT_DIR) {
        this.ROOT_DIR = ROOT_DIR;
        this.socket = socket;
    }

    // parse and get the file
    // write the file to client using http
    @Override
    public void run() {
        DataOutputStream out = null;
        DataInputStream in = null;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            // 一直从out 读取数据，然后做出响应。 因为他们有connect

          //  System.out.println(in.readUTF());
            int i;
            byte[] bytes = new byte[1000];

            while(socket.isConnected() && (i = in.read(bytes))!=-1){  // 尝试读取数据，不知道此时connection关闭了么
                System.out.println("http: request");
                System.out.println(new String(bytes,0,1000));
                System.out.println("parse file...");
                String fileName = getFileName(new String(bytes));
                System.out.println("   "+ fileName);
                File file = getFile(fileName);
                if(file == null){
                    System.out.println("no file");
                }else{
                    System.out.println("there is a file");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out!= null){
                    out.close();
                }
                if(in !=null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getFileName(String request){
        String res = null;
        if(request==null){
            return null;
        }
        StringTokenizer s = new StringTokenizer(request);
        if(s.nextToken().equals("GET")){
            res = s.nextToken(); // get /fir/af/adf.html  http/1.1
            if(res.startsWith("/")){
                int n = res.length();
                res = res.substring(1,n);
            }
            res = ROOT_DIR+"\\"+res;
           // res.replaceAll("\\","/");
        }


        return res;
    }
    public File getFile(String filename){
        if(filename==null){
            return null;
        }
        File res = new File(filename);
        if(res.exists()){
            return res;
        }else{
            System.out.println("file is not exist");
            return null;
        }
    }
    public void sendToClient(File file, OutputStream outputStream){
        if(file ==null || outputStream== null){
            System.out.println("file is null or outputstream is null");
            return;
        }
        FileInputStream fileIn = null;

        try {
            int length = (int)file.length();
            fileIn = new FileInputStream(file);
            byte[] bytes = new byte[length];
            int i = 0;
            while((i= fileIn.read(bytes)) != -1){
                outputStream.write(bytes,0,i);
                outputStream.flush();
                String s1 = "Server: taotao's server";
                outputStream.write(s1.getBytes());
                outputStream.write(new String("Content-Type: "+getMimeType(file)+"/\r\n").getBytes());// content type is necessary
                outputStream.write(new String("Content-Length: "+ i + "/\r\n").getBytes()); // length is also neccessary
            }

        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileIn != null ){
                try {
                    fileIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        if(outputStream!=null){
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
    }


    public String getMimeType(File f) {
        String file = f.toString();
        String type = "";
        if (file.endsWith(".txt")) {
            type = "text/txt";
        } else if (file.endsWith(".html") || file.endsWith("htm")) {
            type = "text/html";
        } else if (file.endsWith(".jpg")) {
            type = "image/jpg";
        } else if (file.endsWith(".png")) {
            type = "image/png";
        } else if (file.endsWith(".jpeg")) {
            type = "image/jpeg";
        } else if (file.endsWith(".gif")) {
            type = "image/gif";
        } else if (file.endsWith(".pdf")) {
            type = "application/pdf";
        } else if (file.endsWith(".mp3")) {
            type = "audio/mpeg";
        } else if (file.endsWith(".class")){
            type = "application/octet-stream";
        } else if (file.endsWith(".mp4")){
            type = "video/mp4";
        }
        return type;
    }

    public static void main(String[] args) {
        tcpsocketReceiver tcpsocketReceiver = new tcpsocketReceiver(5050,50,"192.168.1.8");
        tcpsocketReceiver.runService();
    }
}
