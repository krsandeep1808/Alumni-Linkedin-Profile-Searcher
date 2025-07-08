package com.example.alumni.service;

import com.example.alumni.model.Alumni;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PhantomBusterService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${phantombuster.api.key}")
    private String apiKey;

    @Value("${phantombuster.api.url}")
    private String apiUrl;

    public PhantomBusterService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    /**
     * Search for LinkedIn profiles using PhantomBuster API
     * This is a mock implementation - in real scenario you would configure PhantomBuster
     * with LinkedIn search phantom and pass the search parameters
     */
    public List<Alumni> searchLinkedInProfiles(String university, String designation, String passedYear) {
        try {
            // Prepare the request payload for PhantomBuster
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("id", "linkedin-search-phantom-id"); // Replace with actual phantom ID
            requestBody.put("argument", createSearchArgument(university, designation, passedYear));
            requestBody.put("saveFolder", "linkedin-searches");

            // Make the API call to PhantomBuster
            String response = webClient.post()
                    .uri(apiUrl)
                    .header("X-Phantombuster-Key", apiKey)
                    .header("Content-Type", "application/json")
                    .body(Mono.just(requestBody), Map.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Parse the response and extract alumni data
            return parsePhantomBusterResponse(response, university, passedYear);

        } catch (WebClientResponseException e) {
            System.err.println("Error calling PhantomBuster API: " + e.getMessage());
            // Return mock data for demonstration
            return generateMockAlumniData(university, designation, passedYear);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return generateMockAlumniData(university, designation, passedYear);
        }
    }

    /**
     * Create search argument for PhantomBuster LinkedIn phantom
     */
    private Map<String, Object> createSearchArgument(String university, String designation, String passedYear) {
        Map<String, Object> argument = new HashMap<>();
        argument.put("searches", List.of(university + " " + designation + " " + passedYear));
        argument.put("numberOfProfiles", 50);
        argument.put("csvName", "linkedin-search-results");
        return argument;
    }

    /**
     * Parse PhantomBuster API response to extract alumni data
     */
    private List<Alumni> parsePhantomBusterResponse(String response, String university, String passedYear) {
        List<Alumni> alumni = new ArrayList<>();
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode dataNode = jsonNode.get("data");
            
            if (dataNode != null && dataNode.isArray()) {
                for (JsonNode profile : dataNode) {
                    Alumni alumnus = new Alumni();
                    alumnus.setName(profile.get("name").asText());
                    alumnus.setCurrentRole(profile.get("currentRole").asText());
                    alumnus.setUniversity(university);
                    alumnus.setLocation(profile.get("location").asText());
                    alumnus.setLinkedinHeadline(profile.get("headline").asText());
                    alumnus.setPassedYear(passedYear);
                    alumni.add(alumnus);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing PhantomBuster response: " + e.getMessage());
        }
        return alumni;
    }

    /**
     * Generate mock alumni data for demonstration purposes
     * In real implementation, this would not be needed
     */
    private List<Alumni> generateMockAlumniData(String university, String designation, String passedYear) {
        List<Alumni> mockAlumni = new ArrayList<>();
        
        // Create mock alumni profiles
        mockAlumni.add(new Alumni(
            "John Doe",
            "Software Engineer",
            university,
            "New York, NY",
            "Passionate Software Engineer at XYZ Corp",
            passedYear
        ));
        
        mockAlumni.add(new Alumni(
            "Jane Smith",
            "Data Scientist",
            university,
            "San Francisco, CA",
            "Data Scientist | AI Enthusiast",
            passedYear
        ));
        
        // Add more mock data based on designation
        if (designation.toLowerCase().contains("software")) {
            mockAlumni.add(new Alumni(
                "Mike Johnson",
                "Senior Software Developer",
                university,
                "Seattle, WA",
                "Senior Software Developer | Tech Lead",
                passedYear
            ));
        }
        
        return mockAlumni;
    }
}
