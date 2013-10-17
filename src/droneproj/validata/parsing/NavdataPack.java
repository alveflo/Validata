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
public class NavdataPack implements ListPackInterface{
    private ArrayList<SinglepointList> NavdataLists;

    public NavdataPack(String fileName, double multiplicator,double diff,boolean zeroOffsetCorrection)
    {
        NavdataLists = new ArrayList<>();
        try{
            Scanner plotReader = new Scanner(new BufferedReader(new FileReader(fileName)));
            String nextLine;
            String[] splitLine;
            double offset = 0;
             do
            {
                nextLine = plotReader.nextLine();
            }while(!nextLine.contains("[Sample") && plotReader.hasNextLine());
            nextLine = nextLine.replace("[","");
            nextLine = nextLine.replace("]", "");
            splitLine = nextLine.split("\t");
            for(int i = 3; i < splitLine.length; i++)
            {
                NavdataLists.add(new SinglepointList(splitLine[i]));
            }
            
                                    
            //<editor-fold defaultstate="collapsed" desc="sample cropping code">
            if(diff != 0 && plotReader.hasNextLine())
            {
                boolean trig = false;
                nextLine = plotReader.nextLine();
                splitLine = nextLine.split("\t");
                double[] firstValues = new double[(splitLine.length - 3)];
                
                /*Sample first values*/
                for(int i = 3 ; i < splitLine.length;i++)
                {
                    firstValues[i-3] = Double.parseDouble(splitLine[i]);
                }
                
                /*filter out unwanted values*/
                while(!trig && plotReader.hasNextLine())
                {
                    nextLine = plotReader.nextLine();
                    splitLine = nextLine.split("\t");
                    for(int i = 3; i < splitLine.length;i++)
                    {
                        if(Math.abs((Double.parseDouble(splitLine[i]) - firstValues[i-3])) > diff)
                        {
                            trig = true;
                            break;
                        }
                    }
                    
                    if(trig)
                    {
                        offset = Double.parseDouble(splitLine[1]);
                        for(int i = 3; i < splitLine.length; i++)
                        {
                            NavdataLists.get(i-3).addTime(Double.parseDouble(splitLine[1])-offset);
                            NavdataLists.get(i-3).addValue(Double.parseDouble(splitLine[i]) * multiplicator);
                        }
                    }
                }
            }
            //</editor-fold>
            
            while(plotReader.hasNextLine())
            {
                splitLine = plotReader.nextLine().split("\t");
                for(int i = 0; i < NavdataLists.size();i++)
                {
                    NavdataLists.get(i).addTime((Double.parseDouble(splitLine[1])-offset)/1000);
                    NavdataLists.get(i).addValue(Double.parseDouble(splitLine[i + 3])); // extra padding because of the placement of data in the navdata output
                }
            }
            
                         
            //<editor-fold defaultstate="collapsed" desc="zero offset correction code">            
            if(zeroOffsetCorrection)
            {
                ArrayList<SinglepointList> tempPlaceholderLists = NavdataLists;
                NavdataLists = new ArrayList<>();
                double[] minValueUnderZero = new double[tempPlaceholderLists.size()];
                for(int i = 0;i < minValueUnderZero.length;i++)
                {minValueUnderZero[i] = 0;}
                int cnt = 0;
                for(SinglepointList eL: tempPlaceholderLists)
                {
                    for(int i = 0;i < eL.getSize();i++)
                    {
                        if(eL.getValue(i) < 0)
                        {
                            minValueUnderZero[cnt] = minValueUnderZero[cnt] > eL.getValue(i) ? eL.getValue(i) : minValueUnderZero[cnt];
                        }
                        eL.getValue(i);
                    }
                    cnt++;
                }
                cnt = 0;
                for(SinglepointList eL: tempPlaceholderLists)
                {
                    NavdataLists.add(new SinglepointList(eL.getName()));
                    for(int i = 0;i < eL.getSize();i++)
                    {
                        NavdataLists.get(cnt).addTime(eL.getTime(i));
                        NavdataLists.get(cnt).addValue(eL.getValue(i) + Math.abs(minValueUnderZero[cnt]));
                    }
                    cnt++;
                }
            }
            //</editor-fold>
            
            
        }
        catch(Exception ex)
        {
            System.err.println(ex.toString());        
        }
    }
    
    @Override
    public String[] getNames() {
    String[] names = new String[NavdataLists.size()];
        for(int i = 0; i < NavdataLists.size();i++)
        {
            names[i] = getList(i).getName();
        }
        return names;
    }

    @Override
    public ListInterface getList(int index) {
        return NavdataLists.get(index);
    }

    @Override
    public int getSize() {
        return NavdataLists.size();
    }
    
    public static void main(String [] args)
    {                                       //C:\Users\Jonas\Dropbox\Utvecklingsprojekt\Data\Labbfiler\Test av köring upp&ner\Navdata\Mätning 1_1
        NavdataPack qP = new NavdataPack("C:\\Users\\Jonas\\Dropbox\\Utvecklingsprojekt\\Data\\Labbfiler\\Test av köring upp&ner\\Navdata\\Mätning 1_1\\Attitude TEST1.txt",1,10,true);
        for(SinglepointList sPL:qP.NavdataLists)
        {
            System.out.println(sPL.getName());
            for(int i = 0;i < sPL.getSize();i++)
            {
                System.out.print(sPL.getTime(i) + "\t\t");
                System.out.print(sPL.getValue(i) + "\n");
            }
        }
    }
}
