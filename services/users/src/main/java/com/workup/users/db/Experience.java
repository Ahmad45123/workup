package com.workup.users.db;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder(setterPrefix = "with")
@Getter
@Setter
@Document(collection = "Experience")
public class Experience {
    @Id
    private ObjectId id;

    private String company_name;
    private String job_title;
    private Date employment_start;
    private Date employment_end;
    private String experience_description;
    private String city;
}
