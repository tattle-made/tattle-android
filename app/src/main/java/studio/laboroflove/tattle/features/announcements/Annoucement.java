package studio.laboroflove.tattle.features.announcements;

import com.google.firebase.Timestamp;

class Annoucement {
    private String title;
    private Timestamp timestamp;
    private String text;

    public Annoucement(){

    }

    public String getTitle() {
        return title;
    }

    public Annoucement setTitle(String title) {
        this.title = title;
        return this;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Annoucement setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getText() {
        return text;
    }

    public Annoucement setText(String content) {
        this.text = content;
        return this;
    }
}
