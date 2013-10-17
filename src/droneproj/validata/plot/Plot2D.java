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
 * @author Jonas Jonson
 */
    public class Plot2D extends ApplicationFrame {
        
    private final ChartPanel chartPanel;
    private String title;

    /**
     * Constructor, creates a 2DPlot for only enclosure data
     * @param title name of the plot
     * @param enclosureList samples to be plotted
     */
    public Plot2D(String title, EnclosureList enclosureList) 
    {
        super(title); 
        this.title = title;
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset = incorporateEnclosure(enclosureList, dataset);
        
        chartPanel = new ChartPanel(generateChartPanel(true, dataset));
    }
    
    /**
     * Constructor, creates a 2DPlot for only single point values
     * @param title name of the plot
     * @param singlepointList samples to be plotted 
     */
    public Plot2D (final String title, ListInterface[] singlepointList)
    {
        super(title); 
        this.title = title;
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset = incorporateSinglePoint(singlepointList, dataset);
        
        chartPanel = new ChartPanel(generateChartPanel(false, dataset));
    }
    
    /**
     * Constructor, creates a 2DPlot for single point and enclosure values
     * @param title name of the plot
     * @param enclosureList enclosure sample to be plotted
     * @param singlepointList single point values to be plotted
     */
    public Plot2D (final String title, EnclosureList enclosureList, ListInterface[] singlepointList)
    {
        super(title); 
        this.title = title;
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset = incorporateEnclosure(enclosureList, dataset);
        dataset = incorporateSinglePoint(singlepointList, dataset);
        
        chartPanel = new ChartPanel(generateChartPanel(true, dataset));
    }
    
    /**
     * Generates a JFreeChart from the values provided in the constructor
     * @param isDiffPlot if the samples include enclosure data
     * @param dataset the samples to generate the data from
     * @return A complete JFreeChart
     */
    private JFreeChart generateChartPanel(boolean isDiffPlot, XYDataset dataset)
    {
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Time",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
        XYPlot plot = (XYPlot) chart.getPlot();
        
        if(isDiffPlot)
        {
            XYDifferenceRenderer renderer = new XYDifferenceRenderer(new Color(159,218,224), new Color(227,145,123), true);
            renderer.setSeriesShape(0, new Line2D.Double());
            renderer.setSeriesShape(1, new Line2D.Double());
            renderer.setSeriesShape(2, new Line2D.Double());
            renderer.setSeriesShape(3, new Line2D.Double());
            renderer.setSeriesPaint(0, Color.blue);
            renderer.setSeriesPaint(1, Color.blue);
            plot.setRenderer(0,renderer);
            plot.setRenderer(1,renderer);
        }
        
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

        return chart;
    }
    
    /**
     * Adds a Enclosure to the XYSeries Collection
     * @param eL enclosure samples to be added
     * @param dataset dataset containing all values
     * @return dataset with the new values
     */
    private XYSeriesCollection incorporateEnclosure(EnclosureList eL, XYSeriesCollection dataset)
    {
        XYSeries[] enclosure = DatasetUtils.createEnclosureDataset(eL);
        for(int i = 0;i < enclosure.length;i++)
        {
            dataset.addSeries(enclosure[i]);
        }
        return dataset;
    }
    
    /**
     * Adds some single point lists to the XYSeries Collection
     * @param sL single point samples to be added
     * @param dataset dataset containing all values
     * @return dataset with the new values
     */
    private XYSeriesCollection incorporateSinglePoint(ListInterface[] sL, XYSeriesCollection dataset)
    {
        XYSeries[] singlePoints = DatasetUtils.createSinglepointDataset(sL);
        for(int i = 0; i < singlePoints.length; i++)
        {
            dataset.addSeries(singlePoints[i]);
        }
        return dataset;
    }
    
    /**
     * getter for the chartPanel
     * @return the chartPanel
     */
    public ChartPanel getPanel()
    {
        return chartPanel;
    }
    
    /**
     * @return the title
     */
    @Override
    public String getTitle() {
        return title;
    }
}

