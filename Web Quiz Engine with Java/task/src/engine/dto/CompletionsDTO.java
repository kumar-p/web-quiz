package engine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class CompletionsDTO {
    private long id;
    private LocalDateTime completedAt;
    @JsonIgnore
    private String createdBy;

    public CompletionsDTO(long id, LocalDateTime completedAt, String createdBy) {
        this.id = id;
        this.completedAt = completedAt;
        this.createdBy = createdBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
