/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class QualisysPack implements ListPackInterface{
    private ArrayList<SinglepointList> QualisysLists;
    
    public QualisysPack(String fileName, double multiplicator, double diff)
    {
        QualisysLists = new ArrayList<>();
        try{
            Scanner plotReader = new Scanner(new BufferedReader(new FileReader(fileName)));
            String nextLine;
            String[] splitLine;
            double offset = 0;
            do
            {
                nextLine = plotReader.nextLine();
            }while(!nextLine.contains("Frame") && plotReader.hasNextLine());
            splitLine = nextLine.split("\t");
            for(int i = 2; i < splitLine.length; i++)
            {
                QualisysLists.add(new SinglepointList(splitLine[i]));
            }
            
                                   
            //<editor-fold defaultstate="collapsed" desc="sample cropping code">
            if(diff != 0 && plotReader.hasNextLine())
            {
                boolean trig = false;
                nextLine = plotReader.nextLine();
                splitLine = nextLine.split("\t");
                double[] firstValues = new double[(splitLine.length - 1)];
                
                /*Sample first values*/
                for(int i = 1 ; i < splitLine.length;i++)
                {
                    firstValues[i-1] = Double.parseDouble(splitLine[i]);
                }
                
                /*filter out unwanted values*/
                while(!trig && plotReader.hasNextLine())
                {
                    nextLine = plotReader.nextLine();
                    splitLine = nextLine.split("\t");
                    for(int i = 1; i < splitLine.length;i++)
                    {
                        if(Math.abs((Double.parseDouble(splitLine[i]) - firstValues[i-1])) > diff)
                        {
                            trig = true;
                            break;
                        }
                    }
                    
                    if(trig)
                    {
                        offset = Double.parseDouble(splitLine[1]);
                        for(int i = 2; i < splitLine.length; i++)
                        {
                            QualisysLists.get(i-2).addTime(Double.parseDouble(splitLine[1])-offset);
                            QualisysLists.get(i-2).addValue(Double.parseDouble(splitLine[i]) * multiplicator);
                        }
                    }
                }
            }
            //</editor-fold>
            
            while(plotReader.hasNextLine())
            {
                splitLine = plotReader.nextLine().split("\t");
                for(int i = 0; i < QualisysLists.size();i++)
                {
                    QualisysLists.get(i).addTime(Double.parseDouble(splitLine[1]) - offset);
                    QualisysLists.get(i).addValue(Double.parseDouble(splitLine[i + 2]) * multiplicator);
                }
            }
            
            
        }
        catch(Exception ex)
        {
            System.err.println(ex.toString());        
        }
    }
    
    public static void main(String [] args)
    {
        QualisysPack qP = new QualisysPack("C:/Users/Jonas/Dropbox/Java/utvev/Validata/Plot test/src/Drop boll0517_500mm_b.tsv",1,20);
        for(SinglepointList sPL:qP.QualisysLists)
        {
            System.out.println(sPL.getName());
            for(int i = 0;i < sPL.getSize();i++)
            {
                System.out.print(sPL.getTime(i) + "\t\t");
                System.out.print(sPL.getValue(i) + "\n");
            }
        }
    }

    @Override
    public String[] getNames() {
        String[] names = new String[QualisysLists.size()];
        for(int i = 0; i < QualisysLists.size();i++)
        {
            names[i] = getList(i).getName();
        }
        return names;
    }

    @Override
    public ListInterface getList(int index) {
        return QualisysLists.get(index);
    }
    @Override
    public int getSize() {
        return QualisysLists.size();
    }
}
