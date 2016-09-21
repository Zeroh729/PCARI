package android.zeroh729.com.pcari.data.events;

import android.zeroh729.com.pcari.data.model.response.SurveyResponse;

public class UploadStatusEvent {
    private String responseId;
    private int progress;
    private int totalProgress;

    public UploadStatusEvent(String responseId) {
        this.responseId = responseId;
        progress = 0;
        totalProgress = 0;
    }

    public UploadStatusEvent(String responseId, int progress, int totalProgress) {
        this.responseId = responseId;
        this.progress = progress;
        this.totalProgress = totalProgress;
    }

    public String getResponseId() {
        return responseId;
    }

    public int getProgress() {
        return progress;
    }

    public int getTotalProgress() {
        return totalProgress;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setTotalProgress(int totalProgress) {
        this.totalProgress = totalProgress;
    }
}
