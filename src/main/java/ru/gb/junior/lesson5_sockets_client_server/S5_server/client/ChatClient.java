package ru.gb.junior.lesson5_sockets_client_server.S5_server.client;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json.LoginRequest;
import ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json.LoginResponse;
import ru.gb.junior.lesson5_sockets_client_server.S5_server.data.json.SendMessageRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Класс ChatClient представляет клиента чата, который подключается к серверу и обменивается сообщениями.
 */
public class ChatClient {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        // String clientLogin = "nagibator"; // явно
        // String clientLogin = "User_" + UUID.randomUUID().toString(); // рандомная генерация UUID
        System.out.print("Введите логин: "); // Считываем логин клиента с консоли
        Scanner console = new Scanner(System.in);
        String clientLogin = console.nextLine();

        // 127.0.0.1 или localhost
        try (Socket server = new Socket("localhost", 8888)) {
            System.out.println("Успешно подключились к серверу");

            try (PrintWriter out = new PrintWriter(server.getOutputStream(), true)) {
                Scanner in = new Scanner(server.getInputStream());
                // Создаем и отправляем запрос на логин
                String loginRequest = createLoginRequest(clientLogin);
                out.println(loginRequest);

                // Читаем ответ на запрос логина
                String loginResponseString = in.nextLine();
                if (!checkLoginResponse(loginResponseString)) {
                    // TODO: Можно обогатить причиной, чтобы клиент получал эту причину
                    // (логин уже занят, ошибка аутентификации\авторизации, ...)
                    System.out.println("Не удалось подключиться к серверу");
                    return;
                }

                // client <----------------> server
                // client getUsers ->        server
                // client <- (getUsers|sendMessage from client) server    <--------sendMessage client2
                //

                // Отдельный поток на чтение сообщений
                // ServerListener serverListener = new ServerListener(in);
                // new Thread(serverListener).start();
                new Thread(() -> {
                    while (true) {
                        // TODO: парсим сообщение в AbstractRequest
                        //  по полю type понимаем, что это за request, и обрабатываем его нужным образом
                        String msgFromServer = in.nextLine();
                        System.out.println("Сообщение от сервера: " + msgFromServer);
                    }
                }).start();


                // Предлагаем пользователю выбрать действие
                while (true) {
                    System.out.println("Что хочу сделать?");
                    System.out.println("1. Послать сообщение другу");
                    System.out.println("2. Послать сообщение всем");
                    System.out.println("3. Получить список логинов");

                    String type = console.nextLine();
                    if (type.equals("1")) {
                        // TODO: считываете с консоли логин, кому отправить

                        // Создаем запрос на отправку сообщения
                        SendMessageRequest request = new SendMessageRequest();
                        request.setMessage(console.nextLine());
                        request.setRecipient("unknown"); // TODO указываем логин получателя
                        // Преобразуем запрос в JSON и отправляем на сервер
                        String sendMsgRequest = objectMapper.writeValueAsString(request);
                        out.println(sendMsgRequest);
                    } else if (type.equals("3")) {
                        // TODO: Создаете запрос отправки "всем"
                        // send(get users)
                        // String msgFromServer = in.readLine();
                        // ...

//            serverListener.subscribe("get users", new Consumer<String>() {
//              @Override
//              public void accept(String s) {
//                System.out.println("Список юзеров: " + s);
//              }
//            });

                    }

                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка во время подключения к серверу: " + e.getMessage());
        }

        System.out.println("Отключились от сервера");
    }

    /**
     * Создает JSON-запрос на логин.
     *
     * @param login логин пользователя
     * @return строка JSON-запроса
     */
    private static String createLoginRequest(String login) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(login);

        try {
            return objectMapper.writeValueAsString(loginRequest); // возвращает объект в виде json
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка JSON: " + e.getMessage());
        }
    }

    /**
     * Проверяет ответ на запрос логина.
     *
     * @param loginResponse строка JSON-ответа
     * @return true, если логин успешен, false в противном случае
     */
    private static boolean checkLoginResponse(String loginResponse) {
        try {
            LoginResponse resp = objectMapper.reader().readValue(loginResponse, LoginResponse.class);
            return resp.isConnected();
        } catch (IOException e) {
            System.err.println("Ошибка чтения JSON: " + e.getMessage());
            return false;
        }
    }

//    private static void sleep() {
//    try {
//      Thread.sleep(Duration.ofMinutes(5));
//    } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//    }
//    }

    private static class ServerListener implements Runnable {
        private final Scanner in;
        private final Map<String, List<Consumer<String>>> subscribers = new ConcurrentHashMap<>();

        public ServerListener(Scanner in) {
            this.in = in;
        }

        public void subscribe(String type, Consumer<String> consumer) {
            List<Consumer<String>> consumers = subscribers.getOrDefault(type, new ArrayList<>());
            consumers.add(consumer);
            subscribers.put(type, consumers);
        }

        @Override
        public void run() {
            while (true) {
                String msgFromServer = in.nextLine();

                // TODO: парсим сообщение в AbstractRequest
                //  по полю type понимаем, что это за request, и обрабатываем его нужным образом
                String type = null;

                subscribers.getOrDefault(type, List.of()).forEach(it -> {
                    it.accept(msgFromServer);
                });
                subscribers.remove(type);

                System.out.println("Сообщение от сервера: " + msgFromServer);
            }
        }
    }

}
