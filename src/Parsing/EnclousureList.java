/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Parsing;

import java.util.ArrayList;

/**
 *
 * @author Jonas
 */
public class EnclousureList {
    private String name;
    private ArrayList<Double> time;
    private ArrayList<Double> min;
    private ArrayList<Double> max;
    
    public EnclousureList(String name)
    {
        this.name = name;
        time = new ArrayList<>();
        min = new ArrayList<>();
        max = new ArrayList<>();
    }
    
    public EnclousureList(ArrayList<Double> time,ArrayList<Double> min,ArrayList<Double> max)
    {
        this.name = name;
        this.time = time;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the time
     */
    public ArrayList<Double> getTime() {
        return time;
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
    public ArrayList<Double> getMin() {
        return min;
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
    public ArrayList<Double> getMax() {
        return max;
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
    public String getName() {
        return name;
    }
}
