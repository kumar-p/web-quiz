package engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Arrays;

public class Question {
    private int id;

    @NotBlank(message = "title can not be empty.")
    private String title;

    @NotBlank(message = "text can not be empty")
    private String text;

    @NotNull
    @Size(min = 2, message = "minimum of two options are required")
    private String[] options;

    private int[] answer = new int[0];

    public String[] getOptions() {
        return options;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int[] getAnswer() {
        return answer == null ? new int[0] : answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
