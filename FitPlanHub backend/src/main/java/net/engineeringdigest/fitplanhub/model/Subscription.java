package net.engineeringdigest.fitplanhub.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "subscriptions")
public class Subscription {

    @Id
    private String id;

    private String userId;
    private String planId;

    private String purchaseDate;
    private String expiryDate;
}
