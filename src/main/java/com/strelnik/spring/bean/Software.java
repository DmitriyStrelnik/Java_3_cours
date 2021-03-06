package com.strelnik.spring.bean;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;

@Entity
@Table(name = "software_table")
@Data
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Size(min = 4 , max = 16, message = "Name from 4 to 16")
    @NotNull(message = "Name not be null")
    private String name;

    @Column
    @Size(min = 4 , message = "Description from 4 to 16")
    @NotNull(message = "Description not be null")
    private String description;

    @Column
    @NotNull(message = "File not be null")
    private String softwareName;

    public Software() {
    }

    public Software(@Size(min = 4, max = 16, message = "Name from 4 to 16") @NotNull(message = "Name not be null") String name, @Size(min = 4, message = "Description from 4 to 16") @NotNull(message = "Description not be null") String description, @NotNull(message = "File not be null") String softwareName) {
        this.name = name;
        this.description = description;
        this.softwareName = softwareName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }
}
