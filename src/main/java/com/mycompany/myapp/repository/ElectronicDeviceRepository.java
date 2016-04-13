package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ElectronicDevice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ElectronicDevice entity.
 */
public interface ElectronicDeviceRepository extends JpaRepository<ElectronicDevice,Long> {

}
