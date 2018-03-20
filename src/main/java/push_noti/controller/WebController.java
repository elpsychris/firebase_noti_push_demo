package push_noti.controller;

import adapter.queue.request.Request;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import push_noti.handler.RequestHandler;
import push_noti.model.Greeting;
import push_noti.service.NotiService;
import push_noti.service.ReadyQueueService;
import push_noti.service.WaitingQueueService;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Configuration
public class WebController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    NotiService notiService;
    RequestHandler requestHandler;
    ReadyQueueService readyQueueService;
    WaitingQueueService waitingQueueService;

    public WebController(NotiService notiService, ReadyQueueService readyQueueService, WaitingQueueService waitingQueueService) {
        this.notiService = notiService;
        this.readyQueueService = readyQueueService;
        this.waitingQueueService = waitingQueueService;
        requestHandler = new RequestHandler(this.waitingQueueService, this.readyQueueService, this.notiService);
    }

    @RequestMapping(value = "/requests-waiting", method = RequestMethod.DELETE, consumes = APPLICATION_JSON_VALUE)
    public boolean checkoutWaiting(@RequestBody Request request) {
        System.out.println("Received new CHECKOUT waiting request");
        return requestHandler.checkoutWaitingRequest(request);
    }

    @RequestMapping(value = "/requests-ready", method = RequestMethod.DELETE, consumes = APPLICATION_JSON_VALUE)
    public boolean checkoutReady(@RequestBody Request request) {
        System.out.println("Received new CHECKOUT ready request");
        return requestHandler.checkoutReadyRequest(request);
    }

    @RequestMapping(value = "/requests-waiting", method = RequestMethod.GET)
    public List<Request> getWaitingRequests() {
        return requestHandler.getAllWaitingRequests();
    }

    @RequestMapping(value = "/requests-ready", method = RequestMethod.GET)
    public List<Request> getReadyRequests() {
        return requestHandler.getAllReadyRequests();
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

//    @GetMapping(value = "/requests", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity getRequestList() {
//        List<Request> requestList = requestHandler.getRequestList();
//        return ResponseEntity.status(OK).body(requestList);
//    }
//
//    @GetMapping(value = "/requests/{receiptSeq}", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity getRequestList(@PathVariable String receiptSeq) {
//        List<Request> requestList = requestHandler.getRequestList(receiptSeq);
//        return ResponseEntity.status(OK).body(requestList);
//    }
//
//
//    @PostMapping(value = "/receipt/{receiptSeq}/{subscriberId}", consumes = APPLICATION_JSON_VALUE)
//    public void addNewRequestList(@PathVariable String receiptSeq, @PathVariable String subscriberId, @RequestBody List<Request> newReqList) {
//        requestHandler.addNewRequestList(receiptSeq, newReqList, subscriberId);
//    }
////
////    @PostMapping(value = "/request/{receiptSeq}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
////    public ResponseEntity changeRequest(@PathVariable String receiptSeq, @RequestBody List<Request> requestList) {
////        Result rs = requestHandler.modifyItem(receiptSeq, requestList);
////        return ResponseEntity.status(OK).body(rs);
////    }
//
//    @DeleteMapping(value = "/receipt/{receiptSeq}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity removeRequest(@PathVariable String receiptSeq, @RequestBody List<Request> requestList) {
//        Result rs = requestHandler.removeRequest(receiptSeq, requestList);
//        return ResponseEntity.status(OK).body(rs);
//    }
//
//    @PutMapping(value = "/receipt/{receiptSeq}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity modifyRequest(@PathVariable String receiptSeq, @RequestBody List<Request> requestList) {
//        Result rs = requestHandler.modifyRequest(receiptSeq, requestList);
//        return ResponseEntity.status(OK).body(rs);
//    }


}
