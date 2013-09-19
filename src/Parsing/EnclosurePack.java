package Parsing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jonas
 */
public class EnclosurePack {
    private ArrayList<EnclousureList> enclousureLists;
    
    public EnclosurePack(String fileName){
        enclousureLists = new ArrayList<>();
        try{
            Scanner plotreader = new Scanner (new BufferedReader(new FileReader(fileName)));
            String headers = plotreader.nextLine();
            String [] plots = headers.split("\t");
            String [] points;
            for(int i = 1; i < plots.length;i++)
            {
                enclousureLists.add(new EnclousureList(plots[i]));
            }
            while(plotreader.hasNextLine()){
                headers = plotreader.nextLine();
                plots = headers.split("\t");
                for(int i = 1; i < plots.length;i++)
                {
                    points = plots[i].replace("[", "").replace("]", "").split(",");
                    enclousureLists.get(i-1).addTime(Double.parseDouble(plots[0]));
                    enclousureLists.get(i-1).addMin(Double.parseDouble(points[0]));
                    enclousureLists.get(i-1).addMax(Double.parseDouble(points[1]));
                }
            }
            plotreader.close();
        }
        catch(FileNotFoundException ex)
        {
            
        }
        
    }

    /**
     * @return the enclousureLists
     */
    public ArrayList<EnclousureList> getEnclousureLists() {
        return enclousureLists;
    }
    
    public static void main(String [] args)
    {
        EnclosurePack ab = new EnclosurePack("C:/Users/Jonas/Dropbox/Java/utvev/Validata/Plot test/src/Table");
        for(EnclousureList eL:ab.getEnclousureLists())
        {
            System.out.println("\n" +  eL.getName());
            for(int i = 0; i < eL.getTime().size();i++)
            {
                System.out.print(eL.getTime().get(i) + "\t\t");
                System.out.print(eL.getMin().get(i) + "\t\t");
                System.out.print(eL.getMax().get(i) + "\n");
            }
        }
    }
}
