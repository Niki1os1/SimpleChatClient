import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleChatClient {
    private static final int PORT = 2021;

    private static Socket socket;

    private PrintWriter writer;

    private BufferedReader reader;

    private Scanner scanner;

    public void start() throws IOException {
        socket = new Socket("localhost", PORT);
        writer = new PrintWriter(socket.getOutputStream());
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        reader = new BufferedReader(in);
        scanner = new Scanner(System.in);


        String action;
        do {
            System.out.print("Choose an action: ");
            action = scanner.nextLine();

            if ("help".equals(action)) {
                help();
            } else if ("1".equals(action)) {
                enterName();
            } else if ("2".equals(action)) {
                getUsersList();
            } else if ("3".equals(action)) {
                sendAllMsg();
            } else if ("4".equals(action)) {
                sendPrivateMsg();
            } else if ("5".equals(action)) {
                setCharTopic();
            } else if ("6".equals(action)) {
                getChatTopic();
            } else if ("9".equals(action)) {
                readMessages();
            } else if(!":q".equals(action)){
                System.out.println("Unknown command, enter \"help\".");
            }
        } while (!":q".equals(action));

    }

    private void enterName() throws IOException {
        String name;
        writer.println("1");
        writer.flush();

        System.out.print("Enter your name: ");
        name = scanner.nextLine();
        writer.println(name);
        writer.flush();
    }

    private void getUsersList() throws IOException {
        writer.println("2");
        writer.flush();
        String line;
        System.out.println("A list of users:");
        do {
            line = reader.readLine();
            if (!"end".equals(line)) {
                System.out.println(line);
            }
        } while (!"end".equals(line));
    }

    private void sendAllMsg() throws IOException {
        writer.println("3");
        System.out.print("Enter your message: ");
        String msg = scanner.nextLine();
        writer.println(msg);
        writer.flush();
    }

    private void sendPrivateMsg() throws IOException {
        writer.println("4");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        writer.println(name);
        writer.flush();
        String answer = reader.readLine();
        if ("404".equals(answer)) {
            System.out.println("No such user exists.");
            return;
        } else if ("200".equals(answer)) {
            System.out.print("Enter your message: ");
            String msg = scanner.nextLine();
            writer.println(msg);
            writer.flush();
        }
    }

    private void readMessages() throws IOException {
        writer.println("9");
        writer.flush();
        String msg;
        System.out.println("Incoming messages: ");
        do {
            msg = reader.readLine();
            if (!":end".equals(msg)) {
                System.out.println(msg);
            }
        } while (!":end".equals(msg));
    }

    private void getChatTopic() throws IOException {
        System.out.print("Enter the name of the user for whom you want to know the chat subject: ");
        String name = scanner.nextLine();
        writer.println("6");
        writer.println(name);
        writer.flush();
        String answer = reader.readLine();
        if ("200".equals(answer)) {
            System.out.println("Chat subject: " + reader.readLine());
        } else {
            System.out.println("The chat subject has not been set or the user does not exist.");
        }
    }

    private void setCharTopic() throws IOException {
        System.out.print("Enter the name of the user for whom you want to set a chat subject:");
        String name = scanner.nextLine();
        writer.println("5");
        writer.println(name);
        writer.flush();
        String answer = reader.readLine();
        if ("200".equals(answer)) {
            System.out.print("Enter the chat subject: ");
            String chatTopic = scanner.nextLine();
            writer.println(chatTopic);
            writer.flush();
        } else {
            System.out.println("There is no such user.");
        }
    }

    private void help() {
        System.out.println("1 - enter name");
        System.out.println("2 - a list of users");
        System.out.println("3 - message to all");
        System.out.println("4 - private message");
        System.out.println("5 - set chat subject");
        System.out.println("6 - get chat topic");
        System.out.println("9 - read incoming messages");
        System.out.println(":q - exit");
    }
}
