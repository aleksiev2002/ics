package com.example.ics.repositories;

import com.example.ics.models.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    Optional<ImageEntity> findByChecksum(String checksum);

}
