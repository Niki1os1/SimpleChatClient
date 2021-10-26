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
            System.out.print("Выберите действие: ");
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
                System.out.println("Неизвестная команда, введите \"help\".");
            }
        } while (!":q".equals(action));

    }

    private void enterName() throws IOException {
        String name;
        writer.println("1");
        writer.flush();

        System.out.print("Введите имя: ");
        name = scanner.nextLine();
        writer.println(name);
        writer.flush();
    }

    private void getUsersList() throws IOException {
        writer.println("2");
        writer.flush();
        String line;
        System.out.println("Список пользователей:");
        do {
            line = reader.readLine();
            if (!"end".equals(line)) {
                System.out.println(line);
            }
        } while (!"end".equals(line));
    }

    private void sendAllMsg() throws IOException {
        writer.println("3");
        System.out.print("Введите сообщение: ");
        String msg = scanner.nextLine();
        writer.println(msg);
        writer.flush();
    }

    private void sendPrivateMsg() throws IOException {
        writer.println("4");
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        writer.println(name);
        writer.flush();
        String answer = reader.readLine();
        if ("404".equals(answer)) {
            System.out.println("Такого пользователя не существует.");
            return;
        } else if ("200".equals(answer)) {
            System.out.print("Введите сообщение: ");
            String msg = scanner.nextLine();
            writer.println(msg);
            writer.flush();
        }
    }

    private void readMessages() throws IOException {
        writer.println("9");
        writer.flush();
        String msg;
        System.out.println("Входящие сообщения: ");
        do {
            msg = reader.readLine();
            if (!":end".equals(msg)) {
                System.out.println(msg);
            }
        } while (!":end".equals(msg));
    }

    private void getChatTopic() throws IOException {
        System.out.print("Введите имя пользователя, для которого хотите узнать тему чата: ");
        String name = scanner.nextLine();
        writer.println("6");
        writer.println(name);
        writer.flush();
        String answer = reader.readLine();
        if ("200".equals(answer)) {
            System.out.println("Тема чата: " + reader.readLine());
        } else {
            System.out.println("Тема чата не задана или пользователя не существует.");
        }
    }

    private void setCharTopic() throws IOException {
        System.out.print("Введите имя пользователя, для которого хотите задать тему чата:");
        String name = scanner.nextLine();
        writer.println("5");
        writer.println(name);
        writer.flush();
        String answer = reader.readLine();
        if ("200".equals(answer)) {
            System.out.print("Введите тему чата: ");
            String chatTopic = scanner.nextLine();
            writer.println(chatTopic);
            writer.flush();
        } else {
            System.out.println("Такого пользователя нету");
        }
    }

    private void help() {
        System.out.println("1 - ввести имя");
        System.out.println("2 - список пользователей");
        System.out.println("3 - сообщение всем");
        System.out.println("4 - приватное сообщение");
        System.out.println("5 - установить тему чата");
        System.out.println("6 - получить тему чата");
        System.out.println("9 - прочитать входящие сообщения");
        System.out.println(":q - выход");
    }
}
