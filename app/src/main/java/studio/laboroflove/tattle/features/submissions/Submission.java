package studio.laboroflove.tattle.features.submissions;

public class Submission {
    private String title;
    private String description;
    private String type;
    private String filename;

    public String getTitle() {
        return title==null?"missing data" : title;
    }

    public Submission setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description==null?"missing data" : description;
    }

    public Submission setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getType() {
        return type;
    }

    public Submission setType(String type) {
        this.type = type;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public Submission setFilename(String filename) {
        this.filename = filename;
        return this;
    }
}
