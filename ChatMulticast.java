
package chat;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class ChatMulticast{

    static String username;
    static String ip;
    static int port;
    static volatile boolean conection = false;
    
    public static boolean isConection()
    {
        return conection;
    }
    
    //Criando um construtor para a conexão
    public static void setConection()
    {
        ChatMulticast.conection = conection;
    }
    
    public static void main(String[] args) throws Exception 
    {
    //entrada de dados  
        Scanner input = new Scanner(System.in);
      
        System.out.print("Username: ");
        username = input.nextLine();
        
        System.out.print("IP: ");
        ip = input.nextLine();
        
        System.out.print("Port: ");
        port = input.nextInt();
        
    //IP
        InetAddress chatIp = InetAddress.getByName(ip);
        
    //Socket Multicast para comunicação entre os usuários
        MulticastSocket socket = new MulticastSocket(port);
        socket.setTimeToLive(0); //O socket vai existir enquanto há a execução do programa
        socket.joinGroup(chatIp); //Essa linha liga o socket com o InetAddress, 
        
    //Instanciando a Connection
        Thread thread = new Thread(new Connection(socket, chatIp, port));
        thread.start();
        
        while(true)
        {
        //obter data e hora
            String message = input.nextLine();
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            
        //encerrar sistema
            if(message.equalsIgnoreCase("Sair"))
            {
                conection = true;
                System.exit(0);
            }
            
        //gamibiarra pois n consegu trabalar com Json
            message = ("\nUser: ") +username+ ("\nDate: ") +date+ ("\nTime: ") +time+ ("\nMessage: ") +message;
            
            byte[] convertMessage = new byte[1024];
            convertMessage = message.getBytes();
            
            DatagramPacket sendMessage = new DatagramPacket(convertMessage, convertMessage.length, chatIp, port);
        //envia a mensagem
            socket.send(sendMessage);
        }    
    }
}