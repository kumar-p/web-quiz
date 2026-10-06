package engine.businesslayer;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Completions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;
    private LocalDateTime completedAt;
    private String createdBy;

    public Completions() { }

    public Completions(Quiz quiz, LocalDateTime completedAt, String createdBy) {
        this.quiz = quiz;
        this.completedAt = completedAt;
        this.createdBy = createdBy;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
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
