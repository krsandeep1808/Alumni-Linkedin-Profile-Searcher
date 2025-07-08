package com.example.alumni.repository;

import com.example.alumni.model.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumniRepository extends JpaRepository<Alumni, Long> {
    
    // Find alumni by university
    List<Alumni> findByUniversityContainingIgnoreCase(String university);
    
    // Find alumni by university and passed year
    List<Alumni> findByUniversityContainingIgnoreCaseAndPassedYear(String university, String passedYear);
    
    // Find alumni by name and university (to avoid duplicates)
    Optional<Alumni> findByNameAndUniversity(String name, String university);
    
    // Custom query to search alumni by multiple criteria
    @Query("SELECT a FROM Alumni a WHERE " +
           "LOWER(a.university) LIKE LOWER(CONCAT('%', :university, '%')) AND " +
           "LOWER(a.currentRole) LIKE LOWER(CONCAT('%', :designation, '%')) AND " +
           "a.passedYear = :passedYear")
    List<Alumni> findBySearchCriteria(@Param("university") String university,
                                    @Param("designation") String designation,
                                    @Param("passedYear") String passedYear);
    
    // Find all alumni ordered by created date (most recent first)
    List<Alumni> findAllByOrderByCreatedAtDesc();
}
