package com.fabHotel.skillapp.Repository;

import com.fabHotel.skillapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

