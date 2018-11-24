package studio.laboroflove.tattle.features.feedback;

import com.google.firebase.Timestamp;

public class Feedback {
    private Timestamp timestamp;
    private String feedback;
    //private String user_id;

    public Feedback() {
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Feedback setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getFeedback() {
        return feedback;
    }

    public Feedback setFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }


}
