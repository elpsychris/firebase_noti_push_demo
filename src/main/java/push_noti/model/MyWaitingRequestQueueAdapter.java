package push_noti.model;

import adapter.queue.request.RequestQueueAdapter;
import adapter.queue.request.WaitingRequestQueueAdapter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyWaitingRequestQueueAdapter extends WaitingRequestQueueAdapter {
    public MyWaitingRequestQueueAdapter() {
        super();
    }
}
