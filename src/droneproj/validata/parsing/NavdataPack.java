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

    public NavdataPack(String fileName)
    {
        NavdataLists = new ArrayList<>();
        try{
            Scanner plotReader = new Scanner(new BufferedReader(new FileReader(fileName)));
            String nextLine;
            String[] splitLine;
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
            while(plotReader.hasNextLine())
            {
                splitLine = plotReader.nextLine().split("\t");
                for(int i = 0; i < NavdataLists.size();i++)
                {
                    NavdataLists.get(i).addTime(Double.parseDouble(splitLine[1])/1000);
                    NavdataLists.get(i).addValue(Double.parseDouble(splitLine[i + 3])); // extra padding because of the placement of data in the navdata output
                }
            }
            
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
        NavdataPack qP = new NavdataPack("C:\\Users\\Jonas\\Dropbox\\Utvecklingsprojekt\\Data\\Labbfiler\\Test av köring upp&ner\\Navdata\\Mätning 1_1\\Attitude TEST1.txt");
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
