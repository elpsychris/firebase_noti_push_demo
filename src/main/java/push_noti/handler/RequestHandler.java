package push_noti.handler;

import push_noti.model.Request;
import push_noti.service.RedisService;

import java.util.List;

public class RequestHandler {
    private RedisService redisService;

    public RequestHandler(RedisService redisService) {
        this.redisService = redisService;
    }

    public Request addNewRequest(Request request) {
        long newSeq = redisService.addNewRequest(request);
        request.setSeq(newSeq);
        return request;
    }

    public boolean modifyRequest(long id, Request request) {
        return redisService.modifyRequest(id, request);
    }

    public boolean removeRequest(long id, Request request) {
        return redisService.removeRequest(id, request);
    }

    public List<Request> getRequestList() {
        return redisService.getRequestList();
    }
}
