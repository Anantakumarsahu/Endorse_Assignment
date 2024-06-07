package com.fabHotel.skillapp.Service;
import com.fabHotel.skillapp.Repository.CoworkerRelationshipRepository;
import com.fabHotel.skillapp.Repository.EndorsementRepository;
import com.fabHotel.skillapp.Entity.CoworkerRelationship;
import com.fabHotel.skillapp.Entity.Endorsement;
import com.fabHotel.skillapp.Entity.Skill;
import com.fabHotel.skillapp.Entity.User;
import com.fabHotel.skillapp.Repository.SkillRepository;
import com.fabHotel.skillapp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EndorsementService {

    @Autowired
    private EndorsementRepository endorsementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CoworkerRelationshipRepository coworkerRelationshipRepository;

    public Map<String, List<String>> getEndorsements(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<Endorsement> endorsements = endorsementRepository.findByReviewee(user);

        Map<String, List<String>> endorsementsBySkill = endorsements.stream().collect(Collectors.groupingBy(
                e -> e.getSkill().getName(),
                Collectors.mapping(e -> formatEndorsement(e), Collectors.toList())
        ));

        return endorsementsBySkill;
    }

    private String formatEndorsement(Endorsement endorsement) {
        String reviewerName = "User" + endorsement.getReviewer().getId() +"("+endorsement.getReviewer().getName()+")";
        return reviewerName + " - " + "Score Endorsed: "+endorsement.getScore() + " ( Adjusted Score:" + endorsement.getAdjustedScore() + " with reason: " + endorsement.getReason() + ")";
    }

    public Map<String, Object> endorseSkill(Long revieweeUserID, Long reviewerUserID, Long skillId, int score) {
        User reviewee = userRepository.findById(revieweeUserID).orElseThrow(() -> new RuntimeException("Reviewee not found"));
        User reviewer = userRepository.findById(reviewerUserID).orElseThrow(() -> new RuntimeException("Reviewer not found"));
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"));

        Map<String, Object> response = new HashMap<>();
        boolean hasSkill = reviewee.getUserSkillsList().stream().anyMatch(userSkill -> userSkill.getSkill().getId().equals(skillId));

        if(hasSkill) {
            Endorsement endorsement = new Endorsement();
            endorsement.setReviewee(reviewee);
            endorsement.setReviewer(reviewer);
            endorsement.setSkill(skill);
            endorsement.setScore(score);

            // Calculate adjusted score and reason
            AdjustedScoreResult adjustedScoreResult = calculateAdjustedScore(reviewee, reviewer, skill);

            endorsement.setAdjustedScore(adjustedScoreResult.getAdjustedScore());
            endorsement.setReason(adjustedScoreResult.getReason());
            endorsementRepository.save(endorsement);


            response.put("adjustedScore", adjustedScoreResult.getAdjustedScore());
            response.put("reason", adjustedScoreResult.getReason());
            return response;
        } else {
            response.put("response", "Failed to endorse the "+skill.getName()+" skill");
            response.put("reason", reviewee.getName()+" does not possess the "+skill.getName()+" skill that "+reviewer.getName()+" wants to endorse");
            return response;
        }
    }

    private AdjustedScoreResult calculateAdjustedScore(User reviewee, User reviewer, Skill skill) {
        List<CoworkerRelationship> coworkerRelationships = coworkerRelationshipRepository.findByUsers(reviewee, reviewer);

        boolean isCurrentCoworker = coworkerRelationships.stream()
                .anyMatch(CoworkerRelationship::isCurrentCoworker);

        boolean hasSameSkill = reviewer.getSkills().contains(skill);
        boolean hasSameDepartment = reviewer.getSkills().stream()
                .anyMatch(s -> s.getDepartment().equals(skill.getDepartment()));

        double baseScore = 5.0;
        String reason = "";

        if (isCurrentCoworker) {
            if (hasSameSkill && hasSameDepartment) {
                if (reviewer.getYearsOfExperience() > reviewee.getYearsOfExperience()) {
                    baseScore = 10.0;
                    reason = "Both are current coworkers with the same skills and department. Reviewer has more experience.";
                } else {
                    baseScore = 9.0;
                    reason = "Both are current coworkers with the same skills and department. Reviewer has less experience.";
                }
            } else if (!hasSameSkill && hasSameDepartment) {
                if (reviewer.getYearsOfExperience() > reviewee.getYearsOfExperience()) {
                    baseScore = 7.0;
                    reason = "Both are current coworkers with different skills but the same department. Reviewer has more experience.";
                } else {
                    baseScore = 6.5;
                    reason = "Both are current coworkers with different skills but the same department. Reviewer has less experience.";
                }
            } else if (!hasSameSkill && !hasSameDepartment) {
                if (reviewer.getYearsOfExperience() > reviewee.getYearsOfExperience()) {
                    baseScore = 4.0;
                    reason = "Both are current coworkers with different skills and different departments. Reviewer has more experience.";
                } else {
                    baseScore = 3.5;
                    reason = "Both are current coworkers with different skills and different departments. Reviewer has less experience.";
                }
            }
        } else {
            if (hasSameSkill && hasSameDepartment) {
                if (reviewer.getYearsOfExperience() > reviewee.getYearsOfExperience()) {
                    baseScore = 9.5;
                    reason = "Both are not current coworkers with the same skills and department. Reviewer has more experience.";
                } else {
                    baseScore = 8.5;
                    reason = "Both are not current coworkers with the same skills and department. Reviewer has less experience.";
                }
            } else if (!hasSameSkill && hasSameDepartment) {
                if (reviewer.getYearsOfExperience() > reviewee.getYearsOfExperience()) {
                    baseScore = 6.0;
                    reason = "Both are not current coworkers with different skills but the same department. Reviewer has more experience.";
                } else {
                    baseScore = 5.5;
                    reason = "Both are not current coworkers with different skills but the same department. Reviewer has less experience.";
                }
            } else if (!hasSameSkill && !hasSameDepartment) {
                if (reviewer.getYearsOfExperience() > reviewee.getYearsOfExperience()) {
                    baseScore = 3.0;
                    reason = "Both are not current coworkers with different skills and different departments. Reviewer has more experience.";
                } else {
                    baseScore = 2.5;
                    reason = "Both are not current coworkers with different skills and different departments. Reviewer has less experience.";
                }
            }
        }

        return new AdjustedScoreResult(Math.min(baseScore, 10.0), reason);
    }

    private static class AdjustedScoreResult {
        private final double adjustedScore;
        private final String reason;

        public AdjustedScoreResult(double adjustedScore, String reason) {
            this.adjustedScore = adjustedScore;
            this.reason = reason;
        }

        public double getAdjustedScore() {
            return adjustedScore;
        }

        public String getReason() {
            return reason;
        }
    }



}
