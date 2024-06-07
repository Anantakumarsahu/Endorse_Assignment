package com.fabHotel.skillapp.Entity;

import jakarta.persistence.*;

@Entity
public class CoworkerRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;
    private boolean isCurrentCoworker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public boolean isCurrentCoworker() {
        return isCurrentCoworker;
    }

    public void setCurrentCoworker(boolean currentCoworker) {
        isCurrentCoworker = currentCoworker;
    }
}
