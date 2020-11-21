import java.io.IOException;

public class FolderAlreadyExists extends IOException {

    public FolderAlreadyExists(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
