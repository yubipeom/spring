package com.example.learning.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;

@Data
public class EmployeeJson {
    @JsonProperty("UserID")
    private Integer userId;

    @JsonProperty("Firstname")
    private String firstName;

    @JsonProperty("Lastname")
    private String lastName;

    @JsonProperty("Salary")
    private BigDecimal salary;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("Birthdate")
    private String birthdate;

    @JsonProperty("Active")
    private Boolean isActive;

    @JsonProperty("Level")
    private Byte level;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LocalDate getBirthdateAsLocalDate() {
        if (birthdate == null || birthdate.isEmpty()) {
            return null;
        }
        try {
            String timestampStr = birthdate.replace("/Date(", "").replace(")/", "");
            long timestamp = Long.parseLong(timestampStr);
            return Instant.ofEpochMilli(timestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } catch (Exception e) {
            return null;
        }
    }
}