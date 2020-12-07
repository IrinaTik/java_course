package DataModel;

public class Connection {

    private String lineNumber;
    private String name;

    public Connection(String lineNumber, String name) {
        this.lineNumber = lineNumber;
        this.name = name;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getName() {
        return name;
    }
}
