package com.miwtech.gildedrose;

import com.miwtech.gildedrose.customresponse.CustomResponseMessage;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
public class APITest {
    @Autowired
    private TestRestTemplate restTemplate;

    private HttpEntity<String> validUserHttpEntity;
    @Before
    public void init() {
        String validCreds = "testuser:securepassword";
        validUserHttpEntity = createHttpEntityHeaders(validCreds);
    }
    @Test
    public void purchase_validUser_200() {
        String validPurchaseUrl = "/purchase/1";

        ResponseEntity<CustomResponseMessage> response = restTemplate.exchange(validPurchaseUrl, HttpMethod.GET, validUserHttpEntity, CustomResponseMessage.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void purchase_invalidItem_400()  {
        String invalidPurchaseUrl = "/purchase/999";

        ResponseEntity<CustomResponseMessage> response = restTemplate.exchange(invalidPurchaseUrl, HttpMethod.GET, validUserHttpEntity, CustomResponseMessage.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void purchase_invalidUser_401() throws Exception {
        String validItemUrl = "/purchase/1";
        String invalidCreds = "testuser:incorrectpassword";

        HttpEntity<String> request = createHttpEntityHeaders(invalidCreds);
        ResponseEntity<CustomResponseMessage> response = restTemplate.exchange(validItemUrl, HttpMethod.GET, request, CustomResponseMessage.class);
        CustomResponseMessage customResponseMessage = response.getBody();

        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    private HttpEntity<String> createHttpEntityHeaders(String plainCreds) {

        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        HttpEntity<String> request = new HttpEntity<>(headers);
        return request;
    }

}
