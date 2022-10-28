import java.util.LinkedList;
import java.util.Queue;
    public class ThreadSafeQueue {
        private final Queue<Processor> queue;

        public ThreadSafeQueue() {
            this.queue = new LinkedList<>();
        }

        // Put element in the queue.
        public synchronized void add(Processor elem) {
            queue.add(elem);
            notify();
        }

        // Wait for new element in the queue and return it.
        public synchronized Processor pop() throws InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }
            return this.queue.poll();
        }

        public synchronized int size() {
            return queue.size();
        }
    }
