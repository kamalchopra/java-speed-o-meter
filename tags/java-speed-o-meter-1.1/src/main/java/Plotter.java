import org.jfree.data.time.TimeSeries;

/**
 * @author <a href="mailto:raviw@emerald-associates.com">Ravi Wallau</a>
 */
public interface Plotter
{
    public TimeSeries getReceive();

    public TimeSeries getTransmit();

    public static Plotter EMPTY_PLOTTER =
        new Plotter()
        {
            private final TimeSeries receive = new TimeSeries( "Receive" );
            private final TimeSeries transmit = new TimeSeries( "Transmit" );

            @Override
            public TimeSeries getReceive()
            {
                return receive;
            }

            @Override
            public TimeSeries getTransmit()
            {
                return transmit;
            }
        };
}
