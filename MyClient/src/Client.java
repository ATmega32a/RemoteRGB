import java.net.*;
import java.io.*;
public class Client {
//Не забыть закрыть потоки!!!
    public static void main(String[] args) {
        int serverPort = 6587; // insert your port
        String address = "192.168.0.83"; // insert your IP address

        Socket socket = null;

        InputStream socketInputStream = null;
        OutputStream socketOutputStream = null;

        DataInputStream in = null;
        DataOutputStream out = null;

        BufferedReader keyboard = null;
        try{
            InetAddress ipAddress = InetAddress.getByName(address);
            System.out.println("IP address: " + address + " port: " + serverPort);
            socket = new Socket(ipAddress,serverPort);

            socketInputStream = socket.getInputStream();
            socketOutputStream = socket.getOutputStream();

            in = new DataInputStream(socketInputStream);
            out = new DataOutputStream(socketOutputStream);

            keyboard = new BufferedReader(new InputStreamReader(System.in));

            String line = null;
            System.out.println("Введите что-то и нажмите enter. Отправьте на сервер и ждите ответ");
            System.out.println();

            while(true){
                line = keyboard.readLine();
               // System.out.println("Идёт отправка на сервер ...");
                out.writeUTF(line);
                out.flush();
                line = in.readUTF();
               // System.out.println("Сервер прислал вам ответ:");
               // System.out.println("Вы мне написали \" "+line+" \"");
                System.out.println(line);
                System.out.println();
                System.out.println("Введите что-то и нажмите enter. Отправьте на сервер и ждите ответ");
            }
        } catch (EOFException e1){

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                assert keyboard != null;
                keyboard.close();
                out.close();
                in.close();
                socketOutputStream.close();
                socketInputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}