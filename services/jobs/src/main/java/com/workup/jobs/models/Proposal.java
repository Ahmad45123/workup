package com.workup.jobs.models;

import com.workup.shared.commands.jobs.proposals.JobDuration;
import com.workup.shared.commands.jobs.proposals.ProposalStatus;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Builder(setterPrefix = "with")
@Table("proposals")
public class Proposal {

  @PrimaryKey private Proposal.ProposalPrimaryKey primaryKey;

  @Indexed
  @Column("freelancer_id")
  private String freelancerId;

  private String coverLetter;
  private JobDuration duration;

  @Setter private ProposalStatus status;

  @CassandraType(
      type = CassandraType.Name.LIST,
      typeArguments = CassandraType.Name.UDT,
      userTypeName = "milestone")
  private List<Milestone> milestones;

  @CassandraType(
      type = CassandraType.Name.LIST,
      typeArguments = CassandraType.Name.UDT,
      userTypeName = "attachment")
  private List<Attachment> attachments;

  private Date createdAt;
  private Date updatedAt;

  @PrimaryKeyClass
  @Builder(setterPrefix = "with")
  @Getter
  public static class ProposalPrimaryKey {

    @PrimaryKeyColumn(name = "job_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String jobId;

    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private UUID id;
  }
}
