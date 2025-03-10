package com.uijin.job.batch;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uijin.job.model.JumpitModel.JumpitResponse;
import java.io.IOException;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class findJobCrawling {

  ObjectMapper objectMapper = new ObjectMapper();

  @Scheduled(cron = "0/2 * * * * ?")
  public void findJobCrawling() throws IOException {
    Response response =
        Jsoup.connect("https://jumpit-api.saramin.co.kr/api/positions?jobCategory=1&sort=reg_dt&highlight=false")
            .ignoreContentType(true).execute();

    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    JumpitResponse jumpitResponse = objectMapper.readValue(response.body(), JumpitResponse.class);

    System.out.println(response.body());
  }
}
