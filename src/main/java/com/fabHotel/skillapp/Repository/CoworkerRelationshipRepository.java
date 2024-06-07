package com.fabHotel.skillapp.Repository;

import com.fabHotel.skillapp.Entity.CoworkerRelationship;
import com.fabHotel.skillapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoworkerRelationshipRepository extends JpaRepository<CoworkerRelationship, Long> {

    //CoworkerRelationship findByUser1AndUser2(User user1, User user2);
    @Query("SELECT cr FROM CoworkerRelationship cr WHERE (cr.user1 = :user1 AND cr.user2 = :user2) OR (cr.user1 = :user2 AND cr.user2 = :user1)")
    List<CoworkerRelationship> findByUsers(@Param("user1") User user1, @Param("user2") User user2);


}

