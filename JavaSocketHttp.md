
###  what's the relation with `ServerSocket` and `Socket`
Socket:  client socket
ServerSocket:  server socket;
- input: request from client
- output: based on the request, do something, then give result to the client

`Socket socket = serverSocket.accept();` this method is to let the server listen and if get result, will return a
socket object.
```java
public Socket accept() throws IOException {
        if (isClosed())
            throw new SocketException("Socket is closed");
        if (!isBound())
            throw new SocketException("Socket is not bound yet");
        Socket s = new Socket((SocketImpl) null);
        implAccept(s);
        return s;
    }
```
implAccept() will get the new object socket
- fill with request data
- serverSocket will listen to port, so 1, how does the port listen? 2, what will he get when listening?
serverSocket--> serverSocketImpl.implAccept(Socket s)-->(PlainSocketImpl)SocketImpl.accept() -->AbstractPlainSocketImpl --> DualStackImpl.accept0() --> use java native method
plainSocket  TCP
DualStackImpl  TCP/IP

