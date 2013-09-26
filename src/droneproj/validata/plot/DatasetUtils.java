/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.plot;

import droneproj.validata.parsing.EnclosureList;
import droneproj.validata.parsing.SinglepointList;
import droneproj.validata.utils.ListInterface;
import java.util.ArrayList;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Jonas Jonson
 */
public class DatasetUtils {
    public static XYSeries[] createEnclosureDataset(EnclosureList eL)
    {
        XYSeries[] enclosure = new XYSeries[2];
        enclosure[0] = new XYSeries("Enclosure max");
        enclosure[1] = new XYSeries("Enclosure min");

        for (int i = 0;i<eL.getSize();i++)
        {
            enclosure[0].add(eL.getTime(i), eL.getMax(i));
            enclosure[1].add(eL.getTime(i), eL.getMin(i));
        }
        return enclosure;
    }
    
    public static XYSeries[] createSinglepointDataset(ListInterface[] sLArray)
    {
        XYSeries[] xySeries = new XYSeries[sLArray.length];
        
        int x = 0;
        String name = "";
        for (ListInterface sl : sLArray)
        {
            name = (sl.getName() == null) ? "INPUT" + (x+1) : sl.getName();
            xySeries[x] = new XYSeries(name);
            ArrayList<Double> datapoints = sl.getDataPoints()[1];
            for (int i=0;i<sl.getSize();i++)
            {
                xySeries[x].add(sl.getTime(i),datapoints.get(i));
            }
            x++;
        }
        return xySeries;
    }
}
