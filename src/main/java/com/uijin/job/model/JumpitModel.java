package com.uijin.job.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class JumpitModel {

  @Getter
  @NoArgsConstructor
  public static class JumpitResponse {
    private String message;
    private int status;
    private String code;
    private JumpitPaging result;
  }

  @Getter
  @NoArgsConstructor
  public static class JumpitPaging {
    private int totalCount;
    private int page;
    private String keyword;
    private String keywordType;
    private List<Position> positions;
  }

  @Getter
  @NoArgsConstructor
  public static class Position {
    private long id;
    private String jobCategory;
    private String logo;
    private String imagePath;
    private String title;
    private String companyName;
    private List<String> techStacks;
    private int scrapCount;
    private int viewCount;
    private boolean newcomer;
    private int minCareer;
  }
}
