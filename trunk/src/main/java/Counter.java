//
// Copyright 2011 Emerald Associates, Inc.  All Rights Reserved.
//
// Use of this file other than by Emerald Associates, Inc. is forbidden
// unless otherwise authorized by a separate written license agreement.
//
// $Id$
//

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

import java.util.concurrent.atomic.AtomicLong;

public class Counter
{
    private final String prefix;
    private final Thread printerThread;
    private final AtomicLong bytes = new AtomicLong( 0 );
    private final TimeSeries plotter;

    public Counter( String prefix, final TimeSeries plotter )
    {
        this.prefix = prefix;
        this.plotter = plotter;

        printerThread =
            new Thread()
            {
                @Override
                public void run()
                {
                    long lastRun = System.currentTimeMillis();
                    while( !Thread.interrupted() ) {
                        try {
                            Thread.sleep( 1000 );
                            final long elapsed = System.currentTimeMillis() - lastRun;
                            lastRun = System.currentTimeMillis();
                            final long lastBytes = Counter.this.bytes.getAndSet( 0 );
                            final float mbSec = ( float )lastBytes / 1000000F * ( float )elapsed / 1000F;
                            System.out.println( Counter.this.prefix + " MB/sec: " + mbSec );

                            Counter.this.plotter.add( new Millisecond(), ( double )mbSec );
                        } catch( InterruptedException e ) {
                            break;
                        }
                    }
                }

            };
    }

    public void start()
    {
        printerThread.start();
    }

    public void addBytes( long bytes )
    {
        this.bytes.addAndGet( bytes );
    }

    public void interrupt()
    {
        printerThread.interrupt();
    }
}
