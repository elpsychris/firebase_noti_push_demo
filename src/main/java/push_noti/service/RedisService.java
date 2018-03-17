package push_noti.service;

import org.springframework.stereotype.Service;
import push_noti.model.Request;
import push_noti.repo.RequestRepo;

import java.util.List;

@Service
public class RedisService {
    private RequestRepo requestRepo;

    public RedisService(RequestRepo requestRepo) {
        this.requestRepo = requestRepo;
    }

    public long addNewRequest(Request request) {
        return requestRepo.addNewItem(request);
    }

    public boolean modifyRequest(long id, Request request) {
        return requestRepo.modifyItem(id, request);
    }

    public boolean removeRequest(long id, Request request) {
        return requestRepo.removeItem(id, request);
    }

    public List<Request> getRequestList() {
        return requestRepo.getItemList();
    }

}

