package com.demirserkan.feedbacky;

import com.demirserkan.feedbacky.controller.FeedbackyController;
import com.demirserkan.feedbacky.domain.Feedback;
import com.demirserkan.feedbacky.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedbackyController.class)
public class FeedbackyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private FeedbackService feedbackService;

    static Feedback RECORD_1 = new Feedback();
    static Feedback RECORD_2 = new Feedback();
    static Feedback RECORD_3 = new Feedback();

    @BeforeAll
    public static void init() {
        RECORD_1.setCustomerId("customer-1");
        RECORD_1.setMessage("everything is fine");

        RECORD_2.setCustomerId("customer-2");
        RECORD_2.setMessage("not bad");

        RECORD_3.setCustomerId("customer-3");
        RECORD_3.setMessage("not good enough");
    }

    @Test
    public void getAllFeedbacks_success() throws Exception {
        List<Feedback> expectedResponse = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        Mockito.when(feedbackService.findAll()).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/feedbacky-api/getFeedbacks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].message", is("not good enough")));
    }

    @Test
    public void sendFeedback_success() throws Exception {
        Feedback feedback = new Feedback();
        feedback.setCustomerId("1");
        feedback.setMessage("message");

        Mockito.when(feedbackService.save(feedback)).thenReturn(feedback);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/feedbacky-api/sendFeedback")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(feedback));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.customerId", is("1")))
                .andExpect(jsonPath("$.message", is("message")));
    }

}
