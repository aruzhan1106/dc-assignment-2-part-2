import java.io.IOException;

public class ThreadWorker extends Thread{
    private final ThreadSafeQueue queue;

    public ThreadWorker(ThreadSafeQueue queue) {
        this.queue = queue;
    }

    public void runProcessor(Processor processor_) throws IOException, InterruptedException {
        //Processor processor = new Processor(socket, request);
        processor_.process();
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Wait for new element.
                Processor elem = queue.pop();

                // Stop consuming if null is received.
                if (elem == null) {
                    return;
                }

                // Process element.
                runProcessor(elem);
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
