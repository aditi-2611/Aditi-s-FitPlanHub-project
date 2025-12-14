package net.engineeringdigest.fitplanhub.controller;

import net.engineeringdigest.fitplanhub.model.FitnessPlan;
import net.engineeringdigest.fitplanhub.model.Subscription;
import net.engineeringdigest.fitplanhub.repository.FitnessPlanRepository;
import net.engineeringdigest.fitplanhub.repository.SubscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/feed")
@CrossOrigin
public class FeedController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private FitnessPlanRepository fitnessPlanRepository;

    @GetMapping("/user/{userId}")
    public List<FitnessPlan> getUserFeed(@PathVariable String userId) {

        List<Subscription> subs = subscriptionRepository.findByUserId(userId);

        List<FitnessPlan> plans = new ArrayList<>();

        for (Subscription s : subs) {
            FitnessPlan p = fitnessPlanRepository.findById(s.getPlanId()).orElse(null);
            if (p != null) {
                plans.add(p);
            }
        }

        return plans;
    }
}
