import java.io.IOException;

public class FolderNotCreated extends IOException {

    public FolderNotCreated(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
