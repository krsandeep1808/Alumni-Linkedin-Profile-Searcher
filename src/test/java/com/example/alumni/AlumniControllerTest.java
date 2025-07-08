package com.example.alumni;

import com.example.alumni.controller.AlumniController;
import com.example.alumni.dto.SearchRequest;
import com.example.alumni.model.Alumni;
import com.example.alumni.repository.AlumniRepository;
import com.example.alumni.service.PhantomBusterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlumniController.class)
public class AlumniControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumniRepository alumniRepository;

    @MockBean
    private PhantomBusterService phantomBusterService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSearchAlumni() throws Exception {
        // Arrange
        SearchRequest searchRequest = new SearchRequest("MIT", "Software Engineer", "2020");
        List<Alumni> mockAlumni = Arrays.asList(
            new Alumni("John Doe", "Software Engineer", "MIT", "Boston, MA", "Software Engineer at Google", "2020"),
            new Alumni("Jane Smith", "Software Engineer", "MIT", "San Francisco, CA", "Software Engineer at Apple", "2020")
        );

        when(phantomBusterService.searchLinkedInProfiles(anyString(), anyString(), anyString()))
            .thenReturn(mockAlumni);
        when(alumniRepository.findByNameAndUniversity(anyString(), anyString()))
            .thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(post("/api/alumni/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("John Doe"))
                .andExpect(jsonPath("$.data[1].name").value("Jane Smith"));
    }

    @Test
    public void testGetAllAlumni() throws Exception {
        // Arrange
        List<Alumni> mockAlumni = Arrays.asList(
            new Alumni("Alice Johnson", "Data Scientist", "Stanford", "Palo Alto, CA", "Data Scientist at Meta", "2019"),
            new Alumni("Bob Wilson", "Product Manager", "Stanford", "Seattle, WA", "Product Manager at Amazon", "2019")
        );

        when(alumniRepository.findAllByOrderByCreatedAtDesc()).thenReturn(mockAlumni);

        // Act & Assert
        mockMvc.perform(get("/api/alumni/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("Alice Johnson"))
                .andExpect(jsonPath("$.data[1].name").value("Bob Wilson"));
    }
}
