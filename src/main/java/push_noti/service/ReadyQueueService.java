package push_noti.service;

import adapter.queue.request.Request;
import org.springframework.stereotype.Service;
import push_noti.model.ReadyRequestQueueAdapter;

import java.util.List;

@Service
public class ReadyQueueService {
    private ReadyRequestQueueAdapter readyQueueAdapter;


    public ReadyQueueService(ReadyRequestQueueAdapter readyQueueAdapter) {
        this.readyQueueAdapter = readyQueueAdapter;
    }

    public long checkinRequest(Request request) {
        return readyQueueAdapter.pushRequest(request);
    }

    public Request checkoutRequest(Request request) {
        return readyQueueAdapter.removeRequest(request);
    }

    public List<Request> getAllRequest() {
        return readyQueueAdapter.getRequestList();
    }

    public String getCurHash() {
        return readyQueueAdapter.getCurHash();
    }

}

