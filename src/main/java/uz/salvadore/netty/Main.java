package uz.salvadore.netty;

public class Main {

    public static void main(String[] args) {
        final int port = 8080;
        new NettyServer(port).run(() -> System.out.println("Server running on port: " + port));
    }

}
