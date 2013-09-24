/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.utils;

import java.util.ArrayList;

/**
 *
 * @author Victor
 */
public interface ListInterface {
    /**
     * Returns the name of the list - e.g. x,y,z,
     * @return The name
     */
    public String getName();
    /**
     * Determines whether the list is of single point list or enclosure.
     * @return Single or enclosure.
     */
    public boolean isSinglePointList();
    /**
     * Returns the data points in ArrayLists.
     * @return Data points: [0] Time, [1,2,3,..,x] DataPointLists
     */
    public ArrayList[] getDataPoints();
}
