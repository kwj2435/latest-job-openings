package com.uijin.job.service;

import com.uijin.job.entity.JobEntity;
import com.uijin.job.repository.JobRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

  private final JobRepository jobRepository;

  @Transactional
  public void deleteAll() {
    jobRepository.deleteAll();
  }

  public List<JobEntity> getAll() {
    return jobRepository.findAll();
  }
}
