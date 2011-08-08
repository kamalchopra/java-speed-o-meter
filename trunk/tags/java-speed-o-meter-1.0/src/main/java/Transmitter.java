//
// Copyright 2011 Emerald Associates, Inc.  All Rights Reserved.
//
// Use of this file other than by Emerald Associates, Inc. is forbidden
// unless otherwise authorized by a separate written license agreement.
//
// $Id$
//

import org.jfree.data.time.TimeSeries;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.CRC32;

public class Transmitter
{
    private static final int CRC_BYTES = Long.SIZE / Byte.SIZE;
    private static final int INTEGER_BYTES = Integer.SIZE / Byte.SIZE;

    private final AtomicBoolean running = new AtomicBoolean( true );
    private final TimeSeries plotter;

    public Transmitter( final TimeSeries plotter )
    {
        this.plotter = plotter;
    }

    public Thread transmitThread( final Socket s, final CountDownLatch abortLatch )
    {
        final Thread theThread =
            new Thread()
            {
                @Override
                public void run()
                {
                    final Random r = new Random();
                    final Counter counter = new Counter( "TRANSMIT", plotter );
                    counter.start();

                    try {
                        final OutputStream os = s.getOutputStream();

                        while( !Thread.interrupted() ) {
                            if( !running.get() ) {
                                Thread.sleep( 1000 );
                                continue;
                            }

                            // Every packet is between 1000 and 2000 bytes in size
                            final int packetSize = r.nextInt( 1000 ) + 1000;
                            final byte[] packet = new byte[ packetSize ];
                            r.nextBytes( packet );
                            final CRC32 crc = new CRC32();
                            crc.update( packet );

                            final DataOutputStream dos = new DataOutputStream( os );
                            dos.writeInt( packetSize );
                            counter.addBytes( INTEGER_BYTES );
                            dos.write( packet );
                            counter.addBytes( packetSize );
                            dos.writeLong( crc.getValue() );
                            counter.addBytes( CRC_BYTES );
                        }
                    } catch( InterruptedException e ) {
                        System.out.println( "Transmitter thread interrupted!" );
                    } catch( Exception e ) {
                        System.err.println( "Unexpected error in transmitter thread: " + e.getMessage() );
                    } finally {
                        abortLatch.countDown();
                        counter.interrupt();
                    }
                }
            };

        return theThread;
    }

    public void toggle()
    {
        running.set( !running.get() );
    }
}
