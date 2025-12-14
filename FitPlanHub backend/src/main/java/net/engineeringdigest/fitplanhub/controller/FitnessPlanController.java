package net.engineeringdigest.fitplanhub.controller;

import net.engineeringdigest.fitplanhub.model.FitnessPlan;
import net.engineeringdigest.fitplanhub.repository.FitnessPlanRepository;
import net.engineeringdigest.fitplanhub.repository.UserRepository;
import net.engineeringdigest.fitplanhub.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/plans")
@CrossOrigin
public class FitnessPlanController {

    @Autowired
    private FitnessPlanRepository planRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/create/{trainerId}")
    public String createPlan(
            @PathVariable String trainerId,
            @RequestBody FitnessPlan plan) {

        User trainer = userRepo.findById(trainerId).orElse(null);

        if (trainer == null || !"TRAINER".equals(trainer.getRole())) {
            return "Invalid trainer";
        }

        plan.setTrainerId(trainerId);
        plan.setCreatedAt(LocalDateTime.now().toString());
        plan.setUpdatedAt(LocalDateTime.now().toString());

        planRepo.save(plan);

        return "Plan created successfully";
    }
    @GetMapping("/all")
    public List<FitnessPlan> getAllPlans() {
        return planRepo.findAll();
    }


    @GetMapping("/{planId}")
    public FitnessPlan getPlan(@PathVariable String planId) {
        return planRepo.findById(planId).orElse(null);
    }

    @PutMapping("/update/{trainerId}/{planId}")
    public String updatePlan(
            @PathVariable String trainerId,
            @PathVariable String planId,
            @RequestBody FitnessPlan updatedPlan) {

        FitnessPlan existing = planRepo.findById(planId).orElse(null);

        if (existing == null) {
            return "Plan not found";
        }

        if (!existing.getTrainerId().equals(trainerId)) {
            return "Unauthorized: Cannot edit another trainer’s plan";
        }

        existing.setTitle(updatedPlan.getTitle());
        existing.setDescription(updatedPlan.getDescription());
        existing.setPrice(updatedPlan.getPrice());
        existing.setDurationDays(updatedPlan.getDurationDays());
        existing.setUpdatedAt(LocalDateTime.now().toString());

        planRepo.save(existing);

        return "Plan updated successfully";
    }

    @DeleteMapping("/delete/{trainerId}/{planId}")
    public String deletePlan(
            @PathVariable String trainerId,
            @PathVariable String planId) {

        FitnessPlan existing = planRepo.findById(planId).orElse(null);

        if (existing == null) {
            return "Plan not found";
        }

        if (!existing.getTrainerId().equals(trainerId)) {
            return "Unauthorized: Cannot delete another trainer’s plan";
        }

        planRepo.deleteById(planId);

        return "Plan deleted successfully";
    }

    @GetMapping("/trainer/{trainerId}")
    public List<FitnessPlan> getTrainerPlans(@PathVariable String trainerId) {
        return planRepo.findByTrainerId(trainerId);
    }
}
