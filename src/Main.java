import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        int numOfThreads = (args.length > 1 ? Integer.parseInt(args[1]) : 5);
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;
        int queueLength = args.length > 2 ? Integer.parseInt(args[2]) : 50;
        int activeThreads = 0;

        ThreadSafeQueue queue = new ThreadSafeQueue();

        while(activeThreads < numOfThreads){
            ThreadWorker threadWorker = new ThreadWorker(queue);
            threadWorker.start();
            activeThreads++;
        }

        try (ServerSocket serverSocket = new ServerSocket(port, queueLength)) {
            System.out.println("Web Server is starting up, listening at port " + port + ".");
            System.out.println("You can access http://localhost:" + port + " now.");


            while (true) {
                // Make the server socket wait for the next client request
                Socket socket = serverSocket.accept();
                System.out.println("Got connection!");

                // To read input from the client
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

                // Get request
                HttpRequest request = HttpRequest.parse(input);

                Processor proc = new Processor(socket, request);

                queue.add(proc);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            for (int i = 0; i < activeThreads; i++) {
                queue.add(null);
            }
            System.out.println("Server has been shutdown!");
        }
    }
}