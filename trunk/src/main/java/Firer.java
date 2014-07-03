import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Firer
{
    private static final Plotter p = new Plotter();

    public static void fireUpThreads( final Socket s ) throws InterruptedException
    {
        final CountDownLatch dieLatch = new CountDownLatch( 1 );

        final Transmitter transmitterInstance = new Transmitter( p.getTransmit() );
        final Thread transmitterThread = transmitterInstance.transmitThread( s, dieLatch );
        transmitterThread.start();

        final Receiver receiverInstance = new Receiver( p.getReceive() );
        final Thread receiverThread = receiverInstance.receiverThread( s, dieLatch );
        receiverThread.start();

        final Thread keyboardInterceptorThread = createKeyboardInterceptorThread( transmitterInstance, receiverInstance );
        keyboardInterceptorThread.start();

        try {
            dieLatch.await();
            if( transmitterThread.isAlive() ) {
                transmitterThread.interrupt();
            }
            if( receiverThread.isAlive() ) {
                receiverThread.interrupt();
            }
        } finally {
            keyboardInterceptorThread.interrupt();
        }

        System.out.println( "Firer is finished..." );
    }

    private static Thread createKeyboardInterceptorThread(
        final Transmitter transmitterInstance,
        final Receiver receiverInstance
    )
    {
        return
            new Thread()
        {
            @Override
            public void run()
            {
                final BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );

                while( !Thread.interrupted() ) {
                    final String nextSeq;
                    try {
                        nextSeq = reader.readLine();
                    } catch( IOException e ) {
                        System.err.println( "Can't read input: " + e.getMessage() );
                        break;
                    }

                    if( nextSeq == null ) {
                        continue;
                    }

                    System.out.println( "Command: " + nextSeq );
                    if( nextSeq.trim().toUpperCase().equals( "T" ) ) {
                        transmitterInstance.toggle();
                    } else if( nextSeq.trim().toUpperCase().equals( "R" ) ) {
                        receiverInstance.toggle();
                    } else {
                        System.out.println( "Use T or R to toggle transmission/ receive" );
                    }
                }
            }
        };
    }
}
