package com.scm.service;

import java.util.List;

import com.scm.entity.Feedback;

public interface FeedbackService {

    public Feedback saveFeedback(Feedback feed);

    public Feedback getFeedback(String fid);

    public Feedback updateFeedback(Feedback feed);

    public void deleteFeedback(String fid);

    public List<Feedback> getAllFeedbacks();

}
