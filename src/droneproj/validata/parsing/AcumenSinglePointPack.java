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
public class AcumenSinglePointPack implements ListPackInterface {
       private ArrayList<SinglepointList> AcumenLists;
    
    public AcumenSinglePointPack(String fileName)
    {
        AcumenLists = new ArrayList<>();
        try{
            Scanner plotReader = new Scanner(new BufferedReader(new FileReader(fileName)));
            String nextLine;
            String[] splitLine;
            nextLine = plotReader.nextLine();
            splitLine = nextLine.split("\t");
            
            for(int i = 1; i < splitLine.length; i++)
            {
                AcumenLists.add(new SinglepointList(splitLine[i]));
            }
            
            while(plotReader.hasNextLine())
            {
                splitLine = plotReader.nextLine().split("\t");
                for(int i = 0; i < AcumenLists.size();i++)
                {
                    AcumenLists.get(i).addTime(Double.parseDouble(splitLine[1]));
                    AcumenLists.get(i).addValue(Double.parseDouble(splitLine[i]));
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
        AcumenSinglePointPack qP = new AcumenSinglePointPack("C:\\Users\\Jonas\\Desktop\\klar.txt");
        for(SinglepointList sPL:qP.getAcumenLists())
        {
            System.out.println(sPL.getName());
            for(int i = 0;i < sPL.getSize();i++)
            {
                System.out.print(sPL.getTime(i) + "\t\t");
                System.out.print(sPL.getValue(i) + "\n");
            }
        }
    }

    /**
     * @return the AcumenLists
     */
    public ArrayList<SinglepointList> getAcumenLists() {
        return AcumenLists;
    }

    @Override
    public String[] getNames() {
        String[] names = new String[AcumenLists.size()];
        for(int i = 0; i < AcumenLists.size();i++)
        {
            names[i] = getList(i).getName();
        }
        return names;
    }

    @Override
    public ListInterface getList(int index) {
        return AcumenLists.get(index);
    }
}
