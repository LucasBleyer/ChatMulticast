package chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Connection implements Runnable {
   MulticastSocket socket;
   InetAddress chatIp;
   int port;

    public Connection(){
    }
    
    //construtor
    public Connection(MulticastSocket socket, InetAddress chatIp, int porta)
    {
       this.socket = socket;
       this.chatIp = chatIp;
       this.port = porta;
    }
    
    @Override
    public void run()
    {
       //O while recebe as mensagens através do datagram e dos sockets
       while(!ChatMulticast.conection)
       {
            byte[] recebe = new byte[1024];
            DatagramPacket recebeMensagem = new DatagramPacket(recebe, recebe.length);
            
            //caso haja algum problema com as mensagens
            try
            {
                socket.receive(recebeMensagem);
                String Mensagem = new String(recebeMensagem.getData());
                System.out.println(Mensagem);
            }
            catch(IOException ex)
            {
                System.err.println(ex);
            }
        }
    }
}