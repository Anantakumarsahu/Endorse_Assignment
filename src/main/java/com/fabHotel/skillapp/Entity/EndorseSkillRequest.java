package com.fabHotel.skillapp.Entity;

public class EndorseSkillRequest {
    private Long revieweeUserId;
    private Long reviewerUserId;
    private Long skillId;
    private int score;

    // Getters and setters

    public Long getRevieweeUserId() {
        return revieweeUserId;
    }

    public void setRevieweeUserId(Long revieweeUserId) {
        this.revieweeUserId = revieweeUserId;
    }

    public Long getReviewerUserId() {
        return reviewerUserId;
    }

    public void setReviewerUserId(Long reviewerUserId) {
        this.reviewerUserId = reviewerUserId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

