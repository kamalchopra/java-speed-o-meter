import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {
        if( args.length != 1 ) {
            throw new IllegalArgumentException( "Please specify the port number only (for the listening socket)" );
        }

        final int serverPort = Integer.valueOf( args[ 0 ] );
        final ServerSocket ss = new ServerSocket( serverPort );

        while( true ) {
            System.out.println( "Accepting connections at port " + serverPort + " for all addresses" );
            final Socket s = ss.accept();
            System.out.println( "Accepted connection from " + s.getRemoteSocketAddress() );
            Firer.fireUpThreads( s );
        }
    }
}
