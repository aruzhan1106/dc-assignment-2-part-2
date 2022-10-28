import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Processor of HTTP request.
 */
public class Processor {
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }

    public void process() throws IOException, InterruptedException {

        System.out.println("Got request:");
        System.out.println(request.toString());
        System.out.println(request.getRequestLine());
        System.out.flush();

        PrintWriter output = new PrintWriter(socket.getOutputStream());

        String[] splitRequest = request.getRequestLine().split(" ");

        switch (splitRequest[1]) {
            case "/create/itemid" -> {
                output.println("HTTP/1.1 200 OK");
                output.println("Content-Type: text/html; charset=utf-8");
                output.println();
                output.println("<html>");
                output.println("<head><title>Create</title></head>");
                output.println("<body><p>To create an item please specify the ID</p></body>");
                output.println("</html>");
                output.flush();
                socket.close();
            }
            case "/delete/itemid" -> {
                output.println("HTTP/1.1 200 OK");
                output.println("Content-Type: text/html; charset=utf-8");
                output.println();
                output.println("<html>");
                output.println("<head><title>Delete</title></head>");
                output.println("<body><p>To delete an item please specify the ID</p></body>");
                output.println("</html>");
                output.flush();
                socket.close();
            }
            case "/exec/params" -> {
                // This will print 50 Fibonacci numbers
                output.println("HTTP/1.1 200 OK");
                output.println("Content-Type: text/html; charset=utf-8");
                output.println();
                output.println("<html>");
                output.println("<head><title>hello</title></head>");
                output.println("<body><p>");
                int a = 0, b = 1, c;
                for (int i = 2; i <= 40; i++)
                {
                    c = a + b;
                    a = b;
                    b = c;
                    output.print(b + " ");
                }
                output.println("</p></body>");
                output.println("</html>");
                output.flush();
                Thread.sleep(1000);
                socket.close();
            }
            default -> {
                // We are returning a simple web page now.
                output.println("HTTP/1.1 200 OK");
                output.println("Content-Type: text/html; charset=utf-8");
                output.println();
                output.println("<html>");
                output.println("<head><title>Hello</title></head>");
                output.println("<body><p>Hello, world!</p></body>");
                output.println("</html>");
                output.flush();
                socket.close();
            }
        }
    }
}

