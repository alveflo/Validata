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
public class SinglepointList {
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
    public ArrayList<Double> getValue() {
        return value;
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
    public String getName() {
        return name;
    }
}
