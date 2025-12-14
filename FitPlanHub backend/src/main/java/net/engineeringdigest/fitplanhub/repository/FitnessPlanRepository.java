package net.engineeringdigest.fitplanhub.repository;

import net.engineeringdigest.fitplanhub.model.FitnessPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FitnessPlanRepository extends MongoRepository<FitnessPlan, String> {

    List<FitnessPlan> findByTrainerId(String trainerId);
}
