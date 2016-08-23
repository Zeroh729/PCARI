package android.zeroh729.com.pcari.data.model;

public class Rating {
    private String id;
    private String responseId;
    private String qualitativeResId;
    private int rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQualitativeResId() {
        return qualitativeResId;
    }

    public void setQualitativeResId(String qualitativeResId) {
        this.qualitativeResId = qualitativeResId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }
}
