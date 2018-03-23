package push_noti.model;

import adapter.queue.request.RequestQueueAdapter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReadyRequestQueueAdapter extends RequestQueueAdapter {
    public ReadyRequestQueueAdapter() {
        super("#READY");
    }

    @Override
    public String getCurHash() {
        return super.getCurHash();
    }
}
