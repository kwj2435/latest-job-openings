package com.uijin.job.controller;

import com.uijin.job.entity.JobEntity;
import com.uijin.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobViewController {

  private final JobService jobService;

  @GetMapping
  public String getJobListView(Model model) {
    List<JobEntity> jobList = jobService.getAll();

    Map<String, List<JobEntity>> groupedJobs = jobList.stream()
            .collect(Collectors.groupingBy(JobEntity::getDomain));

    model.addAttribute("groupedJobs", groupedJobs);

    return "jobList";
  }
}
