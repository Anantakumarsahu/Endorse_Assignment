package com.fabHotel.skillapp.Repository;

import com.fabHotel.skillapp.Entity.Endorsement;
import com.fabHotel.skillapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EndorsementRepository extends JpaRepository<Endorsement, Long> {
    List<Endorsement> findByReviewee(User reviewee);
}
