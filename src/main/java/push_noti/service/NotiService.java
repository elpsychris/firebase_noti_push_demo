package push_noti.service;

import adapter.queue.request.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpStatus.OK;

@Service
public class NotiService {
    private static final String FB_SERVER_KEY = "AIzaSyAdDJpSKVRi7IAUelQ2PYJCDG1gyBt1Xls";
    private static final String FB_API_URL = "https://fcm.googleapis.com/fcm/send";

    private ReadyQueueService readyQueuService;

    public NotiService(ReadyQueueService readyQueuService) {
        this.readyQueuService = readyQueuService;
    }

    // TODO: For reference
//    https://firebase.google.com/docs/cloud-messaging/send-message
//    https://fcm.googleapis.com/fcm/send
//    Content-Type:application/json
//    Authorization:key=AIzaSyZ-1u...0GBYzPu7Udno5aA

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {
        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FB_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String fbRes = restTemplate.postForObject(FB_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(fbRes);

    }

    public void notifyAllSubscriber(Request request) {
        System.out.println("Notify all clients");
//        if (request == null) {
//            return new ResponseEntity("OK", OK);
//        }
//        ObjectMapper mapper = new ObjectMapper();
//
//        JSONObject body = new JSONObject();
//        body.put("to", "/topics/" + request.getReceiptSeq());
//        body.put("priority", "HIGH");
//
//        JSONObject noti = new JSONObject();
//        noti.put("title", "Check your request tab!");
//        noti.put("body", "New dish is awaiting to be served");
//
//        try {
//            String json = mapper.writeValueAsString(request.getSubscribers().toArray());
//            noti.put("registration_ids", json);
//
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
////        JSONObject data = new JSONObject();
////        noti.put("Key-1", "Data sample 1");
////        noti.put("Key-2", "Data sample 2");
//
//        body.put("notification", noti);
////        body.put("data", data);
//
//
//        HttpEntity<String> fbReq = new HttpEntity<>(body.toString());
//
//        CompletableFuture<String> pushNoti = send(fbReq);
//        CompletableFuture.allOf(pushNoti).join();
//
//        try {
//            String fbRes = pushNoti.get();
//            return new ResponseEntity<>(fbRes, OK);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>("Push Noti ERROR!", HttpStatus.BAD_REQUEST);
    }

}
