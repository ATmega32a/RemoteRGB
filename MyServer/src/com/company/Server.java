package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class Server {
   private static int port = 6587; // Создаём порт (может быть любым числом от 1025 до 65535)
   private static InputStream socketInputStream = null;
   private static OutputStream socketOutputStream = null;
   private static DataInputStream in = null;
   private static DataOutputStream out = null;

  public static void LinuxShellExecCommands(){
      try {
          Runtime.getRuntime().exec("/bin/bash -c java -version");
          String[] args = new String[] {"/bin/bash", "-c", "java -version", "with", "args"};
          Process proc = new ProcessBuilder(args).start();

      } catch (IOException e) {
          e.printStackTrace();
      }

  }

    public static void main(String[] args) throws SocketException{


        try{
            // Cоздаем сокет сервера и привязываем его к вышеуказанному порту
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Ожидаем запроса от клиента ...");

            // Заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
            Socket socket = serverSocket.accept();
            System.out.println("Клиент успешно подключился к серверу!");
            System.out.println();

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            socketInputStream = socket.getInputStream();
            socketOutputStream = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            in = new DataInputStream(socketInputStream);
            out = new DataOutputStream(socketOutputStream);

            String line = null;
            while (true){
                try {
                    line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
                }catch (EOFException e){}
                assert line != null;
                if(line.equals("/exit")){
                   Server.close();
                   break;
               }

               else {
                   switch (line){
                       case "/help" :
                           String help = "<HELP>\n"+
                                   "\t/start - start system\n"+
                                   "\t/exit - shootdown sever\n"+
                                   "\t/stop - stop system\n"+
                                   "\t/reboot - computer will reboot\n"+
                                   "\t/shutdown - for shutdown computer\n"+
                                   "\t/ledon - for LED on\n"+
                                   "\t/ledoff - for LED off\n"+
                                   "\t/ledRGB.R+ - for increase the brightness of the Red LED\n"+
                                   "\t/ledRGB.G+ - for increase the brightness of the Green LED\n"+
                                   "\t/ledRGB.B+ - for increase the brightness of the Blue LED\n"+
                                   "\t/ledRGB.R- - for decrease the brightness of the Red LED\n"+
                                   "\t/ledRGB.G- - for decrease the brightness of the Green LED\n"+
                                   "\t/ledRGB.B- - for decrease the brightness of the Blue LED\n";
                           System.out.println(help);
                           out.writeUTF(help);

                           break;
                       case "/start" : System.out.println("Start system");
                           out.writeUTF("Start system");
                           break;
                       case "/stop" : System.out.println("Stop system");
                           out.writeUTF("Stop system");
                           break;
                       case "/reboot" : System.out.println("The computer rebooting...");
                           out.writeUTF("The computer rebooting...");
                           Server.LinuxShellExecCommands();
                           break;
                       case "/shutdown" : System.out.println("The computer turns off...");
                           out.writeUTF("The computer turns off...");
                           break;
                       case "/ledon" : System.out.println("LED on");
                           out.writeUTF("LED on");
                           break;
                       case "/ledoff" : System.out.println("LED off");
                           out.writeUTF("LED off");
                           break;
                       case "/ledRGB.R+" : System.out.println("Increased the brightness of the Red LED");
                           out.writeUTF("Increased the brightness of the Red LED");
                           break;
                       case "/ledRGB.G+" : System.out.println("Increased the brightness of the Green LED");
                           out.writeUTF("Increased the brightness of the Green LED");
                           break;
                       case "/ledRGB.B+" : System.out.println("Increased the brightness of the Blue LED");
                           out.writeUTF("Increased the brightness of the Blue LED");
                           break;
                       case "/ledRGB.R-" : System.out.println("Decreased the brightness of the Red LED");
                           out.writeUTF("Decreased the brightness of the Red LED");
                           break;
                       case "/ledRGB.G-" : System.out.println("Decreased the brightness of the Green LED");
                           out.writeUTF("Decreased the brightness of the Green LED");
                           break;
                       case "/ledRGB.B-" : System.out.println("Decreased the brightness of the Blue LED");
                           out.writeUTF("Decreased the brightness of the Blue LED");
                           break;

                       default:  {
                           out.writeUTF("Incorrect command: " + line + "\nTo display help, type \"/help\" ");
                           out.flush();

                       }
                   }
               }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
           Server.close();
        }
    }
public static void close(){
    try {
        out.close();
        in.close();
        socketOutputStream.close();
        socketInputStream.close();
    }catch (Exception e){
        e.printStackTrace();
    }
}
}