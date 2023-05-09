import java.util.Scanner;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.Socket;
import java.io.IOException;
import java.net.UnknownHostException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Client{
    private String username;
    private BufferedReader input;
    private PrintWriter output;
    private Socket socket;

    public Client(String username, Socket socket){
        try {
            this.socket = socket;
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(socket.getOutputStream(), true);
            this.username = username;
        } catch (IOException e){
            // closeEverything
            closeEverything(socket, input, output);
        }
    }

    public void sender(){
        try{
            output.println(username);

            Scanner sc = new Scanner(System.in);

            while (socket.isConnected()){
                
                String message = sc.nextLine();
                if (message.equals("!quit")){
                    output.println("!quit");
                    closeEverything(socket, input, output);
                    System.exit(0);
                } else if (message.equals("!username")){
                    output.println("!username");
                    System.out.println("Enter new username:");
                    String newName = sc.nextLine();
                    username = newName;
                }
                output.println(username + ": " + message);
                
            }
        } catch (Exception e){
            //closeEverything
            closeEverything(socket, input, output);
        }
    }

    public void listener(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String message;
                //System.out.println("Listening...");
                while (socket.isConnected()){
                    try{
                        message = input.readLine();
                        System.out.println(message);
                    } catch (IOException e){
                        //closeEverything
                        closeEverything(socket, input, output);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader input, PrintWriter output){
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

    public static void main(String[] args) throws UnknownHostException, IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Your Username For This Chat: ");
        String username = sc.nextLine();
        Socket socket = new Socket("127.0.0.1", 5001);
        Client client = new Client(username, socket);
        client.listener();
        client.sender();
        
    }
}