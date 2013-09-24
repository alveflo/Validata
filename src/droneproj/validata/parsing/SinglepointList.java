/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.parsing;

import droneproj.validata.utils.ListInterface;
import java.util.ArrayList;

/**
 *
 * @author Jonas
 */
public class SinglepointList implements ListInterface{
    private String name;
    private ArrayList<Double> time;
    private ArrayList<Double> value;
    
    public SinglepointList(String name)
    {
        this.name = name;
        time = new ArrayList<>();
        value = new ArrayList<>();
    }
    
    public SinglepointList(ArrayList<Double> time,ArrayList<Double> value)
    {
        this.name = name;
        this.time = time;
        this.value = value;
    }

    /**
     * @return the time
     */
    public double getTime(int pos) {
        return time.get(pos);
    }

    /**
     * @param time the time to add
     */
    public void addTime(Double time) {
        this.time.add(time);
    }

    /**
     * @return the min
     */
    public double getValue(int pos) {
        return value.get(pos);
    }
    
    public int getSize()
    {
        return time.size();
    }
    /**
     * @param min the min to add
     */
    public void addValue(Double value) {
        this.value.add(value);
    }
    
    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isSinglePointList() {
        return true;
    }

    @Override
    public ArrayList[] getDataPoints() {
        ArrayList<Double>[] DataPoints; 
        DataPoints = new ArrayList[2];
        DataPoints[0] = time;
        DataPoints[1] = value;
        return DataPoints;
    }
}
