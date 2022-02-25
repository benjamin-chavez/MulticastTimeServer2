import java.io.*;
import java.net.*;

public class MulticastClient {

  public static void main(String[] args) {
    System.out.println("Multicast Time Client");
    try (MulticastSocket socket = new MulticastSocket(8888)) {
      InetAddress mcastaddr = InetAddress.getByName("224.0.0.0");
      InetSocketAddress group = new InetSocketAddress(mcastaddr, 7777);   //<= Not sure what the port her is actually doing..
      NetworkInterface netIf = NetworkInterface.getByName("bge0");
      socket.joinGroup(group, netIf);
      System.out.println("Multicast Group Joined)");

      byte[] buffer = new byte[256];
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

      for (int i = 0; i < 100; i++) {
        socket.receive(packet);
        String received = new String(packet.getData());
        System.out.println(received.trim());
      }
      socket.leaveGroup(group, netIf);
    } catch (IOException ex) {
      //TODO: handle exception
    }
    System.out.println("Multicast Time Client Terminated");
  }
}
