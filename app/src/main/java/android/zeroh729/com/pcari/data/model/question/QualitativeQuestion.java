package android.zeroh729.com.pcari.data.model.question;

public class QualitativeQuestion {
    private String id;
    private String responseId;
    private int orderNumber;
    private String question;
    private String questionForRating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionForRating() {
        return questionForRating;
    }

    public void setQuestionForRating(String questionForRating) {
        this.questionForRating = questionForRating;
    }
}
