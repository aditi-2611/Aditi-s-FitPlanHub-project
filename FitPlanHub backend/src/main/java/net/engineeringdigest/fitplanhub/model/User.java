package net.engineeringdigest.fitplanhub.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    // USER or TRAINER
    private String role;

    private String bio;

    private String profileImageUrl;
}
