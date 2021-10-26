import java.io.IOException;

public class Runner {
    public static void main(String[] args) {
        SimpleChatClient client = new SimpleChatClient();
        try {
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
