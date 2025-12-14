package net.engineeringdigest.fitplanhub.controller;

import net.engineeringdigest.fitplanhub.model.FitnessPlan;
import net.engineeringdigest.fitplanhub.model.Subscription;
import net.engineeringdigest.fitplanhub.repository.FitnessPlanRepository;
import net.engineeringdigest.fitplanhub.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private FitnessPlanRepository planRepository;

    @PostMapping("/buy/{userId}/{planId}")
    public String subscribe(@PathVariable String userId, @PathVariable String planId) {

        if (subscriptionRepository.findByUserIdAndPlanId(userId, planId) != null) {
            return "Already subscribed";
        }

        FitnessPlan plan = planRepository.findById(planId).orElse(null);
        if (plan == null) {
            return "Invalid plan";
        }

        Subscription s = new Subscription();
        s.setUserId(userId);
        s.setPlanId(planId);
        s.setPurchaseDate(LocalDateTime.now().toString());
        s.setExpiryDate(LocalDateTime.now().plusDays(plan.getDurationDays()).toString());

        subscriptionRepository.save(s);

        return "Subscription successful";
    }

    @GetMapping("/user/{userId}")
    public List<Subscription> getUserSubscriptions(@PathVariable String userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    @GetMapping("/check/{userId}/{planId}")
    public boolean hasAccess(@PathVariable String userId, @PathVariable String planId) {
        return subscriptionRepository.findByUserIdAndPlanId(userId, planId) != null;
    }
}
