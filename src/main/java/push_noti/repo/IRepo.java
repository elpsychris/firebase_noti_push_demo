package push_noti.repo;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface IRepo<T> {
    long addNewItem(T item) throws JsonProcessingException;

    boolean removeItem(long id, T item);

    boolean modifyItem(long id, T item) throws JsonProcessingException;

    List<T> getItemList();

    T getItem(long id, Class<T> contentClass);

    boolean isRemovable(long id);
}
