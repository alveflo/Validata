/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.plot;

import droneproj.validata.parsing.EnclosureList;
import droneproj.validata.utils.ListInterface;
import java.util.ArrayList;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Jonas Jonson
 */
public class DatasetUtils {
    /**
     * Creates a XYseries from enclosure data
     * @param eL min and max values of a enclosure plot
     * @return complete XYSeries of the enclosure
     */
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
    
    /**
     * creates a XYSeries from the values it gets.
     * Remark, if several lists have the same name all lists will be named as follows,
     * INPUT 1, INPUT 2 and so on.
     * @param sLArray array full of sample lists compirced as singlepoint values
     * each element in the array should be a sample list with name and values.
     * @return complete XYSeries from the lists
     */
    public static XYSeries[] createSinglepointDataset(ListInterface[] sLArray)
    {
        //<editor-fold defaultstate="collapsed" desc="safety check">
        int [] names = new int[sLArray.length];
        boolean safetyMode = false;
        /*
         * Single point list nametest
         */
        for (ListInterface sl : sLArray)
        {
            for(int i = 0; i < sLArray.length;i++)
            {
                if(sl.getName().equalsIgnoreCase(sLArray[i].getName()))
                {
                    names[i]++;
                }
            }
        }
        for(int n:names)
        {
            if(n > 1)
            {
                safetyMode = true;
                break;
            }
        }
        //</editor-fold>
        
        XYSeries[] xySeries = new XYSeries[sLArray.length];
        
        int x = 0;
        String name = "";
        for (ListInterface sl : sLArray)
        {
            name = (sl.getName() == null || safetyMode) ? "INPUT" + (x+1) : sl.getName();
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
