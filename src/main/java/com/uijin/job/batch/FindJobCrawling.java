package com.uijin.job.batch;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uijin.job.entity.JobEntity;
import com.uijin.job.model.JumpitModel;
import com.uijin.job.model.JumpitModel.JumpitResponse;
import java.io.IOException;
import java.util.*;

import com.uijin.job.model.RememberModel;
import com.uijin.job.model.WantedModel;
import com.uijin.job.repository.JobRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class FindJobCrawling {

  private final Map<String, Long> maxJobIdMap = new HashMap<>();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final RestTemplate restTemplate = new RestTemplate();

  private final JobRepository jobRepository;

  @Transactional
//  @Scheduled(cron = "0/20 * * * * ?")
  @Scheduled(initialDelay = 0, fixedDelay = 200000) // 300000ms = 5분
  public void findJobCrawling() throws IOException {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    Response jumpitData = getCrawlingResponse("https://jumpit-api.saramin.co.kr/api/positions?jobCategory=1&sort=reg_dt&highlight=false");
    Response wantedData = getCrawlingResponse("https://www.wanted.co.kr/api/chaos/navigation/v1/results?1741609804248=&job_group_id=518&job_ids=660&job_ids=872&country=kr&job_sort=job.latest_order&years=0&years=10&locations=all&limit=20");
    String rememberData = postCrawlingResponse("https://career-api.rememberapp.co.kr/job_postings/search");

    JumpitResponse jumpitResponse = objectMapper.readValue(jumpitData.body(), JumpitModel.JumpitResponse.class);
    WantedModel.WantedResponse wantedResponse = objectMapper.readValue(wantedData.body(), WantedModel.WantedResponse.class);
    RememberModel.RememberResponse rememberResponse = objectMapper.readValue(rememberData, RememberModel.RememberResponse.class);

    List<JumpitModel.Position> jumpitJobs = jumpitResponse.getResult().getPositions();
    List<WantedModel.WantedJob> wantedJobs = wantedResponse.getData();
    List<RememberModel.RememberJob> rememberJobs = rememberResponse.getData();

    jumpitJobs.sort(Comparator.comparing(JumpitModel.Position::getId));
    wantedJobs.sort(Comparator.comparing(WantedModel.WantedJob::getId));
    rememberJobs.sort(Comparator.comparing(RememberModel.RememberJob::getId));

    List<JobEntity> jobEntityList = new ArrayList<>();
    // 점핏
    for (JumpitModel.Position jumpitJob : jumpitJobs) {
      if(maxJobIdMap.containsKey("J")) {
        if(jumpitJob.getId() > maxJobIdMap.get("J")) {
          maxJobIdMap.put("J", jumpitJob.getId());
          jobEntityList.add(JobEntity.toEntity(jumpitJob));
        }
      } else {
        maxJobIdMap.put("J", jumpitJob.getId());
        jobEntityList.add(JobEntity.toEntity(jumpitJob));
      }
    }

    // 원티드
    for (WantedModel.WantedJob wantedJob : wantedJobs) {
      if(maxJobIdMap.containsKey("W")) {
        if(wantedJob.getId() > maxJobIdMap.get("W")) {
          maxJobIdMap.put("W", wantedJob.getId());
          jobEntityList.add(JobEntity.toEntity(wantedJob));
        }
      } else {
        maxJobIdMap.put("W", wantedJob.getId());
        jobEntityList.add(JobEntity.toEntity(wantedJob));
      }
    }

    // 리멤버
    for (RememberModel.RememberJob rememberJob : rememberJobs) {
      if(maxJobIdMap.containsKey("R")) {
        if(rememberJob.getId() > maxJobIdMap.get("R")) {
          maxJobIdMap.put("R", rememberJob.getId());
          jobEntityList.add(JobEntity.toEntity(rememberJob));
        }
      } else {
        maxJobIdMap.put("R", rememberJob.getId());
        jobEntityList.add(JobEntity.toEntity(rememberJob));
      }
    }

    jobRepository.saveAll(jobEntityList);
  }


  private Response getCrawlingResponse(String url) throws IOException {
    return Jsoup.connect(url).ignoreContentType(true).execute();
  }

  private String postCrawlingResponse(String url) throws IOException {
    String jsonData = "{\n" +
            "  \"search\": {\n" +
            "    \"job_category_names\": [{\n" +
            "      \"level1\": \"SW개발\",\n" +
            "      \"level2\": \"백엔드\"\n" +
            "    }],\n" +
            "    \"organization_type\": \"without_headhunter\"\n" +
            "  },\n" +
            "  \"sort\": \"starts_at_desc\",\n" +
            "  \"page\": 1,\n" +
            "  \"per\": 30,\n" +
            "  \"seed\": 52999777\n" +
            "}";

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    HttpEntity<String> entity = new HttpEntity<>(jsonData, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

    return response.getBody();
  }
}
