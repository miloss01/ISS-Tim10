package com.ISSUberTim10.ISSUberTim10.auth;

import com.postmarkapp.postmark.Postmark;
import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.data.model.message.Message;
import com.postmarkapp.postmark.client.data.model.message.MessageResponse;
import com.postmarkapp.postmark.client.exception.PostmarkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${postmark.key}")
    private String key;
    @Value("${postmark.sender}")
    private String sender;

    public void sendEmail(String receiver, String text) {

        ApiClient client = Postmark.getApiClient(this.key);

        Message message = new Message(this.sender, receiver, "Passenger account activation", text);

        try {
            MessageResponse response = client.deliverMessage(message);
            System.out.println(response);
        } catch (PostmarkException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
