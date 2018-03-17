package push_noti.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import push_noti.handler.RequestHandler;
import push_noti.model.Greeting;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import push_noti.model.Request;
import push_noti.service.NotiService;
import push_noti.service.RedisService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Configuration
public class WebController {

    private final String TOPIC = "RequestList";
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    NotiService notiService;
    RedisService redisService;
    RequestHandler requestHandler;

    public WebController(NotiService notiService, RedisService redisService) {
        this.notiService = notiService;
        this.redisService = redisService;
        requestHandler = new RequestHandler(this.redisService);
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send() {
        System.out.println("Received new GET request");
        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "HIGH");

        JSONObject noti = new JSONObject();
        noti.put("title", "Hi!");
        noti.put("body", "jahhahahahaha");

        JSONObject data = new JSONObject();
        noti.put("Key-1", "Data sample 1");
        noti.put("Key-2", "Data sample 2");

        body.put("notification", noti);
        body.put("data", data);


        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNoti = notiService.send(request);
        CompletableFuture.allOf(pushNoti).join();

        try {
            String fbRes = pushNoti.get();
            return new ResponseEntity<>(fbRes, OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Push Noti ERROR!", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @GetMapping(value = "/requests", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getRequestList() {
        List<Request> requestList = requestHandler.getRequestList();
        return ResponseEntity.status(OK).body(requestList);
    }

    @PostMapping(value = "/request", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity addNewRequest(@RequestBody Request newReq) {
        System.out.println("Received new request " + newReq);
        Request updatedReq = requestHandler.addNewRequest(newReq);
        return ResponseEntity.status(OK).body(updatedReq);
    }

}
