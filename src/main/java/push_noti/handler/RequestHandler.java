package push_noti.handler;

import adapter.queue.request.Request;
import push_noti.model.SimWorker;
import push_noti.service.NotiService;
import push_noti.service.ReadyQueueService;
import push_noti.service.WaitingQueueService;

import java.util.List;

public class RequestHandler {
    private ReadyQueueService readyQueueService;
    private WaitingQueueService waitingQueueService;
    private NotiService notiService;

    public RequestHandler(WaitingQueueService waitingQueueService, ReadyQueueService readyQueueService, NotiService notiService) {
        this.waitingQueueService = waitingQueueService;
        this.readyQueueService = readyQueueService;
        this.notiService = notiService;

        SimWorker simulator = new SimWorker(waitingQueueService, readyQueueService, notiService);
        new Thread(simulator).start();
    }

    public List<Request> getAllWaitingRequests() {
        return waitingQueueService.getAllRequest();
    }

    public List<Request> getAllReadyRequests() {
        return readyQueueService.getAllRequest();
    }

    public boolean checkoutWaitingRequest(Request request) {
        Request confirm = waitingQueueService.checkoutRequest(request);
        if (confirm != null) {
            long result = readyQueueService.checkinRequest(request);
            if (result > 0) {
                notiService.notifyAllSubscriber(request);
                return true;
            }
        }
        return false;
    }

    public boolean checkoutReadyRequest(Request request) {
        Request confirm = readyQueueService.checkoutRequest(request);
        return confirm != null;
    }

//    public void addNewRequestList(String receiptSeq, List<Request> requestList, String subscriberId) {
//        for (Request request : requestList) {
//            long i = readyQueueService.addNewRequest(request) - 1;
//            if (request.getSubscribers() == null) {
//                request.setSubscribers(new HashSet<>());
//            }
//            request.getSubscribers().add(subscriberId);
//            request.setSeq(i);
//            redisReceiptQService.addNewRequest(receiptSeq, request);
//            sourceQService.addNewRequest(request);
//        }
//    }
//
//    public Request addNewRequest(String receiptId, Request request) {
//        long newSeq = readyQueueService.addNewRequest(request);
//        request.setSeq(newSeq);
//        redisReceiptQService.addNewRequest(receiptId, request);
//        return request;
//    }
//
//    public Result modifyRequest(String receiptSeq, List<Request> requestList) {
//
//        for (Request request : requestList) {
//            long seq = request.getSeq();
//            if (seq >= 0) {
//                redisReceiptQService.modifyRequest(receiptSeq, seq, request);
//                readyQueueService.modifyRequest(seq, request);
//            } else {
//                return new Result(Result.ReturnCode.FAILED, "Fail to modify the request");
//            }
//        }
//        return new Result(Result.ReturnCode.SUCCESS, "Request was modified successfully");
//    }
//
//    public Result removeRequest(String receiptSeq, List<Request> requestList) {
//        for (Request request : requestList) {
//            long seq = request.getSeq();
//            if (seq >= 0) {
//                redisReceiptQService.removeRequest(receiptSeq, request);
//                readyQueueService.removeRequest(request);
//            }else {
//                return new Result(Result.ReturnCode.FAILED, "Failed to delete the request");
//            }
//
//        }
//        return new Result(Result.ReturnCode.SUCCESS, "Request was deleted successfully");
//    }
//
//    public List<Request> getRequestList() {
//        return readyQueueService.getRequestList();
//    }
//
//    public List<Request> getRequestList(String receiptSeq) {
//        return redisReceiptQService.getRequestList(receiptSeq);
//    }
//
//    public List<Request> getWaitingRequestList() {
//        return waitingQService.getRequestList();
//    }
//
//    public void checkoutWaitingRequest(Request request) {
//        readyQService.addNewRequest(request);
//        waitingQService.removeRequest(request);
//    }


}
