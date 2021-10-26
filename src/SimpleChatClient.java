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
            System.out.print("�������� ��������: ");
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
                System.out.println("����������� �������, ������� \"help\".");
            }
        } while (!":q".equals(action));

    }

    private void enterName() throws IOException {
        String name;
        writer.println("1");
        writer.flush();

        System.out.print("������� ���: ");
        name = scanner.nextLine();
        writer.println(name);
        writer.flush();
    }

    private void getUsersList() throws IOException {
        writer.println("2");
        writer.flush();
        String line;
        System.out.println("������ �������������:");
        do {
            line = reader.readLine();
            if (!"end".equals(line)) {
                System.out.println(line);
            }
        } while (!"end".equals(line));
    }

    private void sendAllMsg() throws IOException {
        writer.println("3");
        System.out.print("������� ���������: ");
        String msg = scanner.nextLine();
        writer.println(msg);
        writer.flush();
    }

    private void sendPrivateMsg() throws IOException {
        writer.println("4");
        System.out.print("������� ���: ");
        String name = scanner.nextLine();
        writer.println(name);
        writer.flush();
        String answer = reader.readLine();
        if ("404".equals(answer)) {
            System.out.println("������ ������������ �� ����������.");
            return;
        } else if ("200".equals(answer)) {
            System.out.print("������� ���������: ");
            String msg = scanner.nextLine();
            writer.println(msg);
            writer.flush();
        }
    }

    private void readMessages() throws IOException {
        writer.println("9");
        writer.flush();
        String msg;
        System.out.println("�������� ���������: ");
        do {
            msg = reader.readLine();
            if (!":end".equals(msg)) {
                System.out.println(msg);
            }
        } while (!":end".equals(msg));
    }

    private void getChatTopic() throws IOException {
        System.out.print("������� ��� ������������, ��� �������� ������ ������ ���� ����: ");
        String name = scanner.nextLine();
        writer.println("6");
        writer.println(name);
        writer.flush();
        String answer = reader.readLine();
        if ("200".equals(answer)) {
            System.out.println("���� ����: " + reader.readLine());
        } else {
            System.out.println("���� ���� �� ������ ��� ������������ �� ����������.");
        }
    }

    private void setCharTopic() throws IOException {
        System.out.print("������� ��� ������������, ��� �������� ������ ������ ���� ����:");
        String name = scanner.nextLine();
        writer.println("5");
        writer.println(name);
        writer.flush();
        String answer = reader.readLine();
        if ("200".equals(answer)) {
            System.out.print("������� ���� ����: ");
            String chatTopic = scanner.nextLine();
            writer.println(chatTopic);
            writer.flush();
        } else {
            System.out.println("������ ������������ ����");
        }
    }

    private void help() {
        System.out.println("1 - ������ ���");
        System.out.println("2 - ������ �������������");
        System.out.println("3 - ��������� ����");
        System.out.println("4 - ��������� ���������");
        System.out.println("5 - ���������� ���� ����");
        System.out.println("6 - �������� ���� ����");
        System.out.println("9 - ��������� �������� ���������");
        System.out.println(":q - �����");
    }
}
