package com.uijin.job.model;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ZighangModel {
  @Getter
  @NoArgsConstructor
  public static class ZighangResponse {
    private int totalCnt;
    private Recruitments recruitments;
  }

  @Getter
  @NoArgsConstructor
  public static class Recruitments {
    private List<RecruitmentSimple> recruitmentSimpleList;
  }

  @Getter
  @NoArgsConstructor
  public static class RecruitmentSimple {
    private String recruitmentUid;
    private String mainImageUrl;
    private String companyName;
    private String companyAddress;
    private String title;
    private String shortenedUrl;

    public long getId() {
      UUID uuid = UUID.fromString(this.recruitmentUid);
      return uuid.getMostSignificantBits();
    }
  }
}
