package push_noti.model;

import adapter.queue.request.Request;
import adapter.queue.request.WaitingRequestQueueAdapter;

import java.util.List;

public class OperatorWorker implements Runnable {
    private List<Request> requestPool;
    private WaitingRequestQueueAdapter waitingQueue;
    private ReadyRequestQueueAdapter readyQueue;

    private final long TIME_OUT = 5000;
    private final long MAX_QUEUE = 5;

    public OperatorWorker(List<Request> requestPool, WaitingRequestQueueAdapter waitingQueue, ReadyRequestQueueAdapter readyQueue) {
        this.requestPool = requestPool;
        this.waitingQueue = waitingQueue;
        this.readyQueue = readyQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = waitingQueue.popRequest();
                readyQueue.pushRequest(request);
                Thread.sleep(TIME_OUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
