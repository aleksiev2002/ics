package com.example.ics.repositories;

import com.example.ics.models.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface TagRepository extends JpaRepository<TagEntity, Long> {
    @Query("SELECT t FROM tags t WHERE t.id IN (SELECT MIN(te.id) FROM tags te GROUP BY te.name) AND t.name LIKE CONCAT(:prefix, '%')")
    List<TagEntity> findTagEntityByNameStartingWith(String prefix);

    @Query("SELECT t FROM tags t WHERE t.id IN (SELECT MIN(te.id) FROM tags te GROUP BY te.name)")
    List<TagEntity> findTagsByDistinctByUniqueName();

}
