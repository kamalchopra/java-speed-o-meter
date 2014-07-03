import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlotterImpl extends JFrame implements Plotter
{
    private final TimeSeries transmit = new TimeSeries( "Transmitted" );
    private final TimeSeries receive = new TimeSeries( "Received" );

    public static void main( String[] args )
    {
        new PlotterImpl();
    }

    public PlotterImpl() throws HeadlessException
    {
        displayChart();
    }

    private void displayChart()
    {
        final TimeSeriesCollection set = new TimeSeriesCollection();
        set.addSeries( transmit );
        set.addSeries( receive );

        final JFreeChart jfc = ChartFactory.createTimeSeriesChart( "MB/sec", "Time", "MB", set, true, true, false );

        final XYPlot plot = jfc.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange( true );
        axis.setFixedAutoRange( 60000.0 ); // 60 seconds

        JFrame.setDefaultLookAndFeelDecorated( true );

        JPanel panel = new ChartPanel( jfc );

        this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
        this.setTitle( "Da Speed" );
        this.setSize( 500, 500 );

        setContentPane( panel );

        this.pack();
        this.setVisible( true );
    }

    public TimeSeries getReceive()
    {
        return receive;
    }

    public TimeSeries getTransmit()
    {
        return transmit;
    }
}
