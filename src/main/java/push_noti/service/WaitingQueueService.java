package push_noti.service;

import adapter.queue.request.Request;
import org.springframework.stereotype.Service;
import push_noti.model.MyWaitingRequestQueueAdapter;
import push_noti.model.ReadyRequestQueueAdapter;

import java.util.List;

@Service
public class WaitingQueueService {
    private MyWaitingRequestQueueAdapter waitingRequestQueueAdapter;


    public WaitingQueueService(MyWaitingRequestQueueAdapter waitingRequestQueueAdapter) {
        this.waitingRequestQueueAdapter = waitingRequestQueueAdapter;
    }

    public Request checkoutRequest(Request request) {
        return waitingRequestQueueAdapter.removeRequest(request);
    }

    public List<Request> getAllRequest() {
        return waitingRequestQueueAdapter.getRequestList();
    }

    public Request checkoutRequest() {
        return waitingRequestQueueAdapter.popRequest();
    }

    public String getCurHash() {
        return waitingRequestQueueAdapter.getCurHash();
    }

    public Request removeRequest(Request request) {
        return waitingRequestQueueAdapter.removeRequest(request);
    }

    public boolean modifyRequest(Request request) {
        long seq = request.getSeq();
        return waitingRequestQueueAdapter.modifyRequest(seq, request);
    }
}

