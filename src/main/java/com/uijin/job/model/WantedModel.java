package com.uijin.job.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class WantedModel {

  @Getter
  @NoArgsConstructor
  public static class WantedResponse {
    private List<WantedJob> data;
  }

  @Getter
  @NoArgsConstructor
  public static class WantedJob {
    private long id;
    private Company company;
    @JsonProperty("title_img")
    private TitleImage titleImg;
    private Address address;
    private String position;
  }

  @Getter
  @NoArgsConstructor
  public static class Company {
    private String name;
  }

  @Getter
  @NoArgsConstructor
  public static class TitleImage {
    private String thumb;
  }

  @Getter
  @NoArgsConstructor
  public static class Address {
    private String country;
    private String location;
    private String district;

    @Override
    public String toString() {
      return location + " " + district;
    }
  }
}
