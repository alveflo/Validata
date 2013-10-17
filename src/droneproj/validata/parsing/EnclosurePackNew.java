/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.parsing;

import droneproj.validata.utils.ListInterface;
import droneproj.validata.utils.ListPackInterface;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jonas
 */
public class EnclosurePackNew implements ListPackInterface{
    private ArrayList<EnclosureList> enclousureLists;
    
    public EnclosurePackNew(String fileName){
        enclousureLists = new ArrayList<>();
        try{
            Scanner plotreader = new Scanner (new BufferedReader(new FileReader(fileName)));
            String headers = plotreader.nextLine();
            String [] plots = headers.split("\t");
            String [] points;
            for(int i = 1; i < plots.length;i = i +2)
            {
                enclousureLists.add(new EnclosureList(plots[i]));
            }
            while(plotreader.hasNextLine()){
                headers = plotreader.nextLine();
                headers = headers.replace(',', '.');
                plots = headers.split("\t");
                for(int i = 1; i < (plots.length/2)+1;i++)
                {
                    enclousureLists.get(i-1).addTime(Double.parseDouble(plots[0]));
                    enclousureLists.get(i-1).addMin(Double.parseDouble(plots[i*2-1]));
                    enclousureLists.get(i-1).addMax(Double.parseDouble(plots[i*2]));
                }
            }
            plotreader.close();
        }
        catch(Exception ex)
        {
            System.err.println(ex.toString());
        }
        
    }

    /**
     * @return the enclousureLists
     */
    public ArrayList<EnclosureList> getEnclousureLists() {
        return enclousureLists;
    }
    
    public static void main(String [] args)
    {
        droneproj.validata.parsing.EnclosurePack ab = new droneproj.validata.parsing.EnclosurePack("C:/Users/Jonas/Dropbox/Java/utvev/Validata/Plot test/src/Table");
        for(EnclosureList eL:ab.getEnclousureLists())
        {
            System.out.println("\n" +  eL.getName());
            for(int i = 0; i < eL.getSize();i++)
            {
                System.out.print(eL.getTime(i) + "\t\t");
                System.out.print(eL.getMin(i) + "\t\t");
                System.out.print(eL.getMax(i) + "\n");
            }
        }
    }

    @Override
    public String[] getNames() {
        String[] names = new String[enclousureLists.size()];
        for(int i = 0; i < enclousureLists.size();i++)
        {
            names[i] = getList(i).getName();
        }
        return names;
    }

    @Override
    public ListInterface getList(int index) {
        return enclousureLists.get(index);
    }
    
    @Override
    public int getSize() {
        return this.enclousureLists.size();
    }
}

