import java.io.Serializable;

public class Note implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String message;
    private String filePath;

    public Note(String title, String message, String filePath) {
        this.title = title;
        this.message = message;
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getFilePath() {
        return filePath;
    }
}
