package engine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class QuizDTO {
    private long id;

    @NotBlank(message = "title can not be empty.")
    private String title;

    @NotBlank(message = "text can not be empty")
    private String text;

    @NotNull
    @Size(min = 2, message = "minimum of two options are required")
    private String[] options;

    @JsonIgnore
    private String createdBy;

    private int[] answer = new int[0];

    public QuizDTO(
            long id,
            String title,
            String text,
            @NotNull @Size(min = 2, message = "minimum of two options are required") String[] options,
            String createdBy,
            int[] answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.createdBy = createdBy;
        this.answer = answer;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int[] getAnswer() {
        return answer == null ? new int[0] : answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
