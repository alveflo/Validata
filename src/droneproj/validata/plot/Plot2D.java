/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.plot;

import droneproj.validata.parsing.EnclosureList;
import droneproj.validata.parsing.SinglepointList;
import droneproj.validata.utils.ListInterface;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author Victor
 */
public class Plot2D extends ApplicationFrame {
    private EnclosureList enclosureList;
    private final ChartPanel chartPanel;
    private String title;
    private ListInterface[] singlepointList;
    public Plot2D (final String title, EnclosureList enclosureList, ListInterface[] singlepointList)
    {
        super(title); 
        this.title = title;
        this.enclosureList = enclosureList;
        this.singlepointList = singlepointList;
        
        XYDataset dataset = createSampleDataset();
        
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
        XYPlot plot = (XYPlot) chart.getPlot();
        XYDifferenceRenderer renderer = new XYDifferenceRenderer(new Color(159,218,224), new Color(227,145,123), true);
        renderer.setSeriesShape(0, new Line2D.Double());
        renderer.setSeriesShape(1, new Line2D.Double());
        renderer.setSeriesShape(2, new Line2D.Double());
        renderer.setSeriesShape(3, new Line2D.Double());
        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesPaint(1, Color.blue);
        
        XYSplineRenderer splineRenderer = new XYSplineRenderer();
        splineRenderer.setSeriesPaint(0,Color.BLACK);
        splineRenderer.setSeriesLinesVisible(0, false);
        splineRenderer.setSeriesShapesVisible(0, false);
        splineRenderer.setSeriesShape(0, new Line2D.Double());
        splineRenderer.setSeriesStroke(
            0, new BasicStroke(
                1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, null, 0.0f
            )
        );
        
        plot.setRenderer(2, splineRenderer);

        plot.setRenderer(0,renderer);
        plot.setRenderer(1,renderer);

        chartPanel = new ChartPanel(chart);
    }
    
    public ChartPanel getPanel()
    {
        return chartPanel;
    }
    
    private XYDataset createSampleDataset()
    {
        XYSeries enclosureMax = new XYSeries("Enclosure max");
        XYSeries enclosureMin = new XYSeries("Enclosure min");
        XYSeries[] xySeries = new XYSeries[singlepointList.length];

        for (int i = 0;i<enclosureList.getSize();i++)
        {
            enclosureMax.add(enclosureList.getTime(i), enclosureList.getMax(i));
            enclosureMin.add(enclosureList.getTime(i), enclosureList.getMin(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(enclosureMax);
        dataset.addSeries(enclosureMin);
        
        int x = 0;
        String name = "";
        for (ListInterface sl : singlepointList)
        {
            name = (sl.getName() == null) ? "INPUT" + (x+1) : sl.getName();
            xySeries[x] = new XYSeries(name);
            ArrayList<Double> datapoints = sl.getDataPoints()[1];
            for (int i=0;i<sl.getSize();i++)
            {
                xySeries[x].add(sl.getTime(i),datapoints.get(i));
            }
            dataset.addSeries(xySeries[x]);
            x++;
        }
        return dataset;
    }

    /**
     * @return the title
     */
    @Override
    public String getTitle() {
        return title;
    }
}


