package com.demirserkan.feedbacky.service;

import com.demirserkan.feedbacky.domain.Feedback;
import com.demirserkan.feedbacky.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public Feedback save(Feedback feedback) {
        feedback.setInsertDate(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }
}
