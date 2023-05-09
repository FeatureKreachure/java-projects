import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;



public class Server{
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{
            // Loop that waits for client to connect
            System.out.println("Starting Server...");
            while(!serverSocket.isClosed()){
                // Stops here until client connects
                Socket socket = serverSocket.accept();
                //print that client connected
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
                System.out.println(clientHandler.getName() + " has connected.");
                //System.out.println(clientHandler.username + "has entered.");
            }
        } catch (IOException e){
            //ignore
        }
    }

    public void closeServer(){
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        } 
    }

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(5001);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}