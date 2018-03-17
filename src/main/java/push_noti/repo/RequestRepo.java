package push_noti.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import push_noti.model.Request;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@Repository
public class RequestRepo extends GenericRepo<Request> {

    public RequestRepo() {
        super(new Jedis(), new ObjectMapper());
    }

    @Override
    public boolean isRemovable(long id) {
        Request request = super.getItem(id, Request.class);
        return request.isDone();
    }

    @Override
    Request parseJSON2POJO(String json) {
        try {
            return mapper.readValue(json, Request.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean setRequestDone(long id)  {
        Request request = getItem(id, Request.class);
        if (request.isDone()) {
            return false;
        }
        request.setDone(true);
        modifyItem(id, request);
        return true;
    }
}
