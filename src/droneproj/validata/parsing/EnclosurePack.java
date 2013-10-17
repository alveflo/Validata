package droneproj.validata.parsing;

import droneproj.validata.utils.ListInterface;
import droneproj.validata.utils.ListPackInterface;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jonas
 */
public class EnclosurePack implements ListPackInterface{
    private ArrayList<EnclosureList> enclousureLists;
    
    public EnclosurePack(String fileName,double multiplicator){
        enclousureLists = new ArrayList<>();
        try{
            Scanner plotreader = new Scanner (new BufferedReader(new FileReader(fileName)));
            String headers = plotreader.nextLine();
            String [] plots = headers.split("\t");
            String [] points;
            for(int i = 1; i < plots.length;i++)
            {
                enclousureLists.add(new EnclosureList(plots[i]));
            }
            while(plotreader.hasNextLine()){
                headers = plotreader.nextLine();
                plots = headers.split("\t");
                for(int i = 1; i < plots.length;i++)
                {
                    points = plots[i].replace("[", "").replace("]", "").split(",");
                    enclousureLists.get(i-1).addTime(Double.parseDouble(plots[0]));
                    enclousureLists.get(i-1).addMin(Double.parseDouble(points[0]) * multiplicator);
                    enclousureLists.get(i-1).addMax(Double.parseDouble(points[1]) * multiplicator);
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
        EnclosurePack ab = new EnclosurePack("C:/Users/Jonas/Dropbox/Java/utvev/Validata/Plot test/src/Table",1);
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
