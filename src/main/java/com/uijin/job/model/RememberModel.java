package com.uijin.job.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class RememberModel {
  @Getter
  @NoArgsConstructor
  public static class RememberResponse {
    private List<RememberJob> data;
  }

  @Getter
  @NoArgsConstructor
  public static class RememberJob {
    private long id;
    private String title;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
    @JsonProperty("normalized_address")
    private Address normalizedAddress;
    private Organization organization;
  }

  @Getter
  @NoArgsConstructor
  public static class Address {
    private String level1;
    private String level2;

    @Override
    public String toString() {
      return level1 + " " + level2;
    }
  }

  @Getter
  @NoArgsConstructor
  public static class Organization {
    private String name;
  }
}
