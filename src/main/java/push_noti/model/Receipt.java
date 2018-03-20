package push_noti.model;


import adapter.queue.request.Request;

import java.util.List;

public class Receipt {
    private String seq;
    private String table;
    private List<Request> requestList;

    public Receipt() {
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }
}
