package push_noti.model;

import adapter.queue.request.Request;
import adapter.queue.request.WaitingRequestQueueAdapter;
import push_noti.service.NotiService;
import push_noti.service.ReadyQueueService;
import push_noti.service.WaitingQueueService;

import java.util.List;

public class SimWorker implements Runnable {
    private WaitingQueueService waitingQueue;
    private ReadyQueueService readyQueue;
    private NotiService notiService;

    private final long TIME_OUT = 30000;
    private final long MAX_QUEUE = 5;

    public SimWorker(WaitingQueueService waitingQueue, ReadyQueueService readyQueue, NotiService notiService) {
        this.waitingQueue = waitingQueue;
        this.readyQueue = readyQueue;
        this.notiService = notiService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = waitingQueue.checkoutRequest();
                if (request != null) {
                    readyQueue.checkinRequest(request);
                    notiService.notifyAllSubscriber(request);
                    System.out.printf("===========|  %s is ready!\n", request.getItemSeq());
                }
                Thread.sleep(TIME_OUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
