import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ClientHandler implements Runnable{
    private BufferedReader input;
    private PrintWriter output;
    private static ArrayList<ClientHandler> handlers = new ArrayList<>();
    private String username;
    public Socket socket;

    public ClientHandler(Socket socket){
        try{
            this.socket = socket;

            // initialise PrintWriter output and set auto-flush to true
            this.output = new PrintWriter(socket.getOutputStream(), true);

            //initialise BufferedReader input
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = input.readLine();
            handlers.add(this);
            //System.out.println(username + " has entered the chat.");
            broadcaster(username + " has entered the chat.");
        } catch (IOException e){
            //ignore
            closeEverything(socket, input, output);
        }
    }

    public void closeEverything(Socket socket, BufferedReader input, PrintWriter output){
        removeClient();
        try {
            if (input != null){
                input.close();
            }
            if (output != null){
                output.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void broadcaster(String messageSend){
        for (ClientHandler c : handlers){
            try {
                if (!c.username.equals(username)){
                    c.output.println(messageSend);
                }
            } catch (Exception e){
                closeEverything(socket, input, output);
            }
        }
    }

    public void removeClient(){
        handlers.remove(this);
        broadcaster("SERVER: " + username + " has left the chat.");
    }

    public String getName(){
        return this.username;
    }

    @Override
    public void run(){
        String msgFromClient;

        while (socket.isConnected()){
            try {
                msgFromClient = input.readLine();
                if (!msgFromClient.equals(null)){
                    if (msgFromClient.equals("!quit")){
                        closeEverything(socket, input, output);
                    } else if (msgFromClient.equals("!username")){
                        String newName = input.readLine();
                        broadcaster(username + " has changed their name to " + newName);
                        username = newName;
                    } else {
                        broadcaster(msgFromClient);
                    }
                } else {
                    continue;
                }
            } catch (IOException e){
                closeEverything(socket, input, output);
                break;
            }
        }
    }
}