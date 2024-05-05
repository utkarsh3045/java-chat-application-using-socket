// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.InputStreamReader;
// import java.io.OutputStreamWriter;
// import java.net.Socket;
// import java.util.Scanner;

// public class ClientRoom {

//     private Socket socket;
//     private BufferedReader bufferedReader;
//     private BufferedWriter bufferedWriter;
//     private String username;

//     public ClientRoom(Socket socket, String username) {
//         try {
//             this.socket = socket;
//             this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//             this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             this.username = username;
            
//             bufferedWriter.write(username);
//             bufferedWriter.newLine();
//             bufferedWriter.flush();
//         } catch (Exception e) {
//             closeEverything();
//         }
//     }

//     public void sendMessage() {
//         try {
//             Scanner scanner = new Scanner(System.in);
//             while (socket.isConnected()) {
//                 String messageToSend = scanner.nextLine();
//                 bufferedWriter.write(username + " : " + messageToSend);
//                 bufferedWriter.newLine();
//                 bufferedWriter.flush();
//             }
//         } catch (Exception e) {
//             closeEverything();
//         }
//     }

//     public void listenForMessage() {
//         new Thread(() -> {
//             try {
//                 String msgFromGroupChat;
//                 while ((msgFromGroupChat = bufferedReader.readLine()) != null) {
//                     System.out.println(msgFromGroupChat);
//                 }
//             } catch (Exception e) {
//                 closeEverything();
//             }
//         }).start();
//     }

//     public void closeEverything() {
//         try {
//             if (bufferedReader != null) {
//                 bufferedReader.close();
//             }
//             if (bufferedWriter != null) {
//                 bufferedWriter.close();
//             }
//             if (socket != null) {
//                 socket.close();
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     public static void main(String[] args) {
//         try {
//             Scanner scanner = new Scanner(System.in);
//             System.out.println("Enter your username for the group chat: ");
//             String username = scanner.nextLine();
//             Socket socket = new Socket("localhost", 1234);
//             ClientRoom client = new ClientRoom(socket, username);
//             client.listenForMessage();
//             client.sendMessage();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientRoom {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public ClientRoom(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception e) {
            closeEverything();
        }
    }

    public void sendMessage() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + " : " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            closeEverything();
        }
    }

    public void listenForMessage() {
        new Thread(() -> {
            try {
                String msgFromGroupChat;
                while ((msgFromGroupChat = bufferedReader.readLine()) != null) {
                    System.out.println(msgFromGroupChat);
                }
            } catch (Exception e) {
                closeEverything();
            }
        }).start();
    }

    public void closeEverything() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Enter your username for the group chat: ");
                String username = scanner.nextLine();
                Socket socket = new Socket("localhost", 1234);
                ClientRoom client = new ClientRoom(socket, username);
                client.listenForMessage();
                client.sendMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
