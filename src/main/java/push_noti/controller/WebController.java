package push_noti.controller;

import push_noti.model.Greeting;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import push_noti.service.AndroidPushNotiService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class WebController {

    private final String TOPIC = "RequestList";
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    AndroidPushNotiService androidPushNotiService;

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

        CompletableFuture<String> pushNoti = androidPushNotiService.send(request);
        CompletableFuture.allOf(pushNoti).join();

        try {
            String fbRes = pushNoti.get();
            return new ResponseEntity<>(fbRes, HttpStatus.OK);
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
}
