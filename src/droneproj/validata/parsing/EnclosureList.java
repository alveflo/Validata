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
public class EnclosureList implements ListInterface{
    private String name;
    private ArrayList<Double> time;
    private ArrayList<Double> min;
    private ArrayList<Double> max;
    
    public EnclosureList(String name)
    {
        this.name = name;
        time = new ArrayList<>();
        min = new ArrayList<>();
        max = new ArrayList<>();
    }
    
    public EnclosureList(String name, ArrayList<Double> time,ArrayList<Double> min,ArrayList<Double> max)
    {
        this.name = name;
        this.time = time;
        this.min = min;
        this.max = max;
    }
    public int getSize()
    {
        return time.size();
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
    public double getMin(int pos) {
        return min.get(pos);
    }

    /**
     * @param min the min to add
     */
    public void addMin(Double min) {
        this.min.add(min);
    }

    /**
     * @return the max
     */
    public double getMax(int pos) {
        return max.get(pos);
    }

    /**
     * @param max the max to add
     */
    public void addMax(Double max) {
        this.max.add(max);
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
        return false;
    }

    @Override
    public ArrayList[] getDataPoints() {
        ArrayList<Double>[] DataPoints; 
        DataPoints = new ArrayList[3];
        DataPoints[0] = time;
        DataPoints[1] = min;
        DataPoints[2] = max;
        return DataPoints;
    }
}
