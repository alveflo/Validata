/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.plot;

import droneproj.validata.parsing.EnclosureList;
import droneproj.validata.parsing.SinglepointList;
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
    private SinglepointList[] singlepointList;
    public Plot2D (final String title, EnclosureList enclosureList, SinglepointList[] singlepointList)
    {
        super(title);
        this.enclosureList = enclosureList;
        this.singlepointList = singlepointList;
    }
    
    private XYDataset createSampleDataset()
    {
        XYSeries enclosureMax = new XYSeries("Enclosure over");
        XYSeries enclosureMin = new XYSeries("Enclosure under");
        XYSeries[] xySeries = new XYSeries[singlepointList.length];

        for (int i = 0;i<enclosureList.getSize();i++)
        {
            enclosureMax.add(enclosureList.getTime(i), enclosureList.getMax(i));
            enclosureMin.add(enclosureList.getTime(i), enclosureList.getMax(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(enclosureMax);
        dataset.addSeries(enclosureMin);
        
        int x = 0;
        for (SinglepointList sl: singlepointList)
        {
            for (int i=0;i<sl.getSize();i++)
            {
                xySeries[x].add(sl.getTime(i),sl.getValue(i));
            }
            dataset.addSeries(xySeries[x]);
            x++;
        }
        return dataset;
    }
}


