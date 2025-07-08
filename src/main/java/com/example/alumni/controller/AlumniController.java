package com.example.alumni.controller;

import com.example.alumni.dto.ApiResponse;
import com.example.alumni.dto.SearchRequest;
import com.example.alumni.model.Alumni;
import com.example.alumni.repository.AlumniRepository;
import com.example.alumni.service.PhantomBusterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumni")
public class AlumniController {

    private final AlumniRepository alumniRepository;
    private final PhantomBusterService phantomBusterService;

    @Autowired
    public AlumniController(AlumniRepository alumniRepository, PhantomBusterService phantomBusterService) {
        this.alumniRepository = alumniRepository;
        this.phantomBusterService = phantomBusterService;
    }

    /**
     * Search Alumni Profiles API
     */
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<Alumni>>> searchAlumni(@Valid @RequestBody SearchRequest searchRequest) {
        List<Alumni> alumni = phantomBusterService.searchLinkedInProfiles(
                searchRequest.getUniversity(),
                searchRequest.getDesignation(),
                searchRequest.getPassedYear()
        );

        // Save to the database and avoid duplicates
        alumni.forEach(alumnus -> {
            if (alumniRepository.findByNameAndUniversity(alumnus.getName(), alumnus.getUniversity()).isEmpty()) {
                alumniRepository.save(alumnus);
            }
        });

        return ResponseEntity.ok(ApiResponse.success(alumni));
    }

    /**
     * Get Saved Alumni Profiles
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Alumni>>> getAllAlumni() {
        List<Alumni> alumni = alumniRepository.findAllByOrderByCreatedAtDesc();
        return ResponseEntity.ok(ApiResponse.success(alumni));
    }
}
