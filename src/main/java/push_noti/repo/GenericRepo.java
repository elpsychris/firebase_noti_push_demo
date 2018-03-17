package push_noti.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import push_noti.utils.HashGenerator;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericRepo<T> implements IRepo<T> {
    private final String REQ_SET_KEY = "queue#reqList";
    private final String UPDATE_HASH_KEY = "queue#updateHash";
    private String myHash;
    ObjectMapper mapper;

    private List<T> itemList;

    private Jedis jedis;

    GenericRepo(Jedis jedis, ObjectMapper mapper) {
        this.jedis = jedis;
        this.myHash = this.regenerateServerHash();
        this.mapper = mapper;
    }

    @Override
    public long addNewItem(T item) {
        String data;
        long newItemSeq;
        try {
            data = mapper.writeValueAsString(item);
            newItemSeq = jedis.lpush(REQ_SET_KEY, data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return -1;
        }
        regenerateServerHash();
        return newItemSeq;

    }


    @Override
    public boolean modifyItem(long id, T item) {
        String data;
        try {
            data = mapper.writeValueAsString(item);
            jedis.lset(REQ_SET_KEY, id, data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        regenerateServerHash();
        return true;
    }

    @Override
    public boolean removeItem(long id, T item) {
        String data;
        try {
            data = mapper.writeValueAsString(item);
            if (isRemovable(id)) {
                jedis.lrem(REQ_SET_KEY, -1, data);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        regenerateServerHash();
        return true;
    }

    public abstract boolean isRemovable(long id);

    @Override
    public List<T> getItemList() {
        if (isLatest() && itemList != null) {
            return this.itemList;
        }

        List<String> jsonList = jedis.lrange(REQ_SET_KEY, 0, -1);

        this.itemList = new ArrayList<>();
        for (String jsonStr : jsonList) {
            T newT = parseJSON2POJO(jsonStr);
            itemList.add(newT);
        }

        return this.itemList;
    }

    @Override
    public T getItem(long id, Class<T> contentClass) {
        String json = jedis.lindex(REQ_SET_KEY, id);
        return parseJSON2POJO(json);
    }

    private boolean isLatest() {
        return this.myHash.equals(getServerHash());
    }

    private String regenerateServerHash() {
        String redisNewHash = HashGenerator.generateHash();
        jedis.set(UPDATE_HASH_KEY, redisNewHash);
        return redisNewHash;
    }

    private String getServerHash() {
        return jedis.get(UPDATE_HASH_KEY);
    }

    abstract T parseJSON2POJO(String json);
}
