import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Consumer {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String getUrl = "http://94.198.50.185:7081/api/users";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(getUrl, String.class);
        List<String> cookies = forEntity.getHeaders().get("Set-Cookie");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie",cookies.stream().collect(Collectors.joining(";") ) );

        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("id", "3");
        jsonToSend.put("name", "James");
        jsonToSend.put("lastName", "Brown");
        jsonToSend.put("age", "26");

        HttpEntity<Map<String, String>> postRequest = new HttpEntity<>(jsonToSend, headers);
        String postUrl = "http://94.198.50.185:7081/api/users";
        String postResponse = restTemplate.exchange(postUrl, HttpMethod.POST ,postRequest, String.class).getBody();

        StringBuilder sb = new StringBuilder();
        sb.append(postResponse);

        jsonToSend.clear();

        jsonToSend.put("id", "3");
        jsonToSend.put("name", "Thomas");
        jsonToSend.put("lastName", "Shelby");
        jsonToSend.put("age", "26");

        HttpEntity<Map<String, String>> putRequest = new HttpEntity<>(jsonToSend, headers);
        String putUrl = "http://94.198.50.185:7081/api/users";
        sb.append(restTemplate.exchange(putUrl, HttpMethod.PUT, putRequest, String.class).getBody() );

        HttpEntity<Map<String, String>> deleteRequest = new HttpEntity<>(headers);
        int id = 3;
        String deleteUrl = "http://94.198.50.185:7081/api/users/" + id;
        sb.append(restTemplate.exchange(deleteUrl, HttpMethod.DELETE ,deleteRequest, String.class).getBody() );

        System.out.println(sb);
    }
}
