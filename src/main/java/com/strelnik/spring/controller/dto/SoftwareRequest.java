package com.strelnik.spring.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;

public class SoftwareRequest {
    @Size(min = 4 , max = 16, message = "Name from 4 to 16")
    @NotNull(message = "Name not be null")
    private String name;

    @Size(min = 4 , message = "Description from 4 to 16")
    @NotNull(message = "Description not be null")
    private String description;

    @NotNull(message = "File not be null")
    private File file;

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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
