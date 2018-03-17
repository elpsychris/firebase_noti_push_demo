package push_noti.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class AndroidPushNotiService {
    private static final String FB_SERVER_KEY = "AIzaSyAdDJpSKVRi7IAUelQ2PYJCDG1gyBt1Xls";
    private static final String FB_API_URL = "https://fcm.googleapis.com/fcm/send";
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
}
