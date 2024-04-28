package com.workup.jobs.models;

import com.workup.shared.enums.jobs.Experience;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.SASI;
import org.springframework.data.cassandra.core.mapping.SASI.IndexMode;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Builder(setterPrefix = "with")
@Table("jobs")
public class Job {

  @PrimaryKey private UUID id;

  private String title;
  private String description;
  private String location;
  private double budget;

  @SASI(indexMode = IndexMode.CONTAINS)
  @Column("search_index")
  private String searchIndex;

  @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
  private String[] skills;

  private Experience experienceLevel;

  @Setter private boolean isActive;

  @Indexed
  @Column("client_id")
  private String clientId;

  private Date createdAt;
  private Date updatedAt;

  public static class JobBuilder {

    public JobBuilder withTitle(String title) {
      this.title = title;
      updateSearchIndex();
      return this;
    }

    public JobBuilder withDescription(String desc) {
      this.description = desc;
      updateSearchIndex();
      return this;
    }

    public JobBuilder withSkills(String[] skills) {
      this.skills = skills;
      updateSearchIndex();
      return this;
    }

    private void updateSearchIndex() {
      String skills_with_commas = this.skills != null ? String.join(",", this.skills) : "";
      this.searchIndex = String.join(",", this.title, this.description, skills_with_commas);
    }
  }
}
