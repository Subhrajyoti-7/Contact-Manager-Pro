package com.scm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entity.Feedback;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, String> {

}
