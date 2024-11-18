package com.scm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entity.Feedback;
import com.scm.repo.FeedbackRepo;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Override
    public Feedback saveFeedback(Feedback feed) {
        return feedbackRepo.save(feed);
    }

    @Override
    public Feedback getFeedback(String fid) {
        return feedbackRepo.findById(fid).get();
    }

    @Override
    public Feedback updateFeedback(Feedback feed) {
        return feedbackRepo.save(feed);
    }

    @Override
    public void deleteFeedback(String fid) {
        feedbackRepo.deleteById(fid);
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepo.findAll();
    }

}
