package com.uijin.job.controller;

import com.uijin.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/job")
@RequiredArgsConstructor
public class JobController {

  private final JobService jobService;

  @DeleteMapping
  public void deleteAll() {
    jobService.deleteAll();
  }
}
