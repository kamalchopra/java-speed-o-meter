//
// Copyright 2011 Emerald Associates, Inc.  All Rights Reserved.
//
// Use of this file other than by Emerald Associates, Inc. is forbidden
// unless otherwise authorized by a separate written license agreement.
//
// $Id$
//

import java.io.IOException;
import java.net.Socket;

public class Client
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {
        if( args.length != 2 ) {
            throw new IllegalArgumentException( "Please specify the server name and port number (for the client socket)" );
        }

        final String server = args[ 0 ];
        final int portNumber = Integer.valueOf( args[ 1 ] );

        final Socket s = new Socket( server, Integer.valueOf( portNumber ) );

        System.out.println( "Connected to " + server + " at port " + portNumber );

        Firer.fireUpThreads( s );
    }
}
