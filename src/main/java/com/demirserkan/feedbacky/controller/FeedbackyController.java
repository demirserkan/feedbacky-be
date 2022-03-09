package com.demirserkan.feedbacky.controller;

import com.demirserkan.feedbacky.domain.Feedback;
import com.demirserkan.feedbacky.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/feedbacky-api")
@Api(value = "Feedbacky Api documentation")
public class FeedbackyController {

    private final FeedbackService feedbackService;

    @PostMapping("/sendFeedback")
    @ApiOperation(value = "New feedback message adding method")
    public Feedback sendFeedback(@Valid @RequestBody @ApiParam("sendFeedback parameter") Feedback feedback) {
        log.info("[sendFeedback()] feedback request is ::: {}", feedback.toString());
        return feedbackService.save(feedback);
    }

    @GetMapping("/getFeedbacks")
    @ApiOperation(value = "Feedback list method")
    public List<Feedback> getFeedbacks() {
        return feedbackService.findAll();
    }

}
