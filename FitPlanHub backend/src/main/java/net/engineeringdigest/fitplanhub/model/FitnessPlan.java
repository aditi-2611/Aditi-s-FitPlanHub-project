package net.engineeringdigest.fitplanhub.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "fitness_plans")
public class FitnessPlan {

    @Id
    private String id;

    private String trainerId;
    private String title;
    private String description;
    private double price;
    private int durationDays;

    private String createdAt;
    private String updatedAt;
}
