package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.House;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the House entity.
 */
public interface HouseRepository extends JpaRepository<House,Long> {

}
