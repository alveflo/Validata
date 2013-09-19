/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class QualisysPack {
    private ArrayList<SinglepointList> QualisysLists;
    
    public QualisysPack(String fileName)
    {
        QualisysLists = new ArrayList<>();
        try{
            Scanner plotReader = new Scanner(new BufferedReader(new FileReader(fileName)));
            String nextLine;
            String[] splitLine;
            do
            {
                nextLine = plotReader.nextLine();
            }while(!nextLine.contains("Frame") && plotReader.hasNextLine());
            splitLine = nextLine.split("\t");
            for(int i = 2; i < splitLine.length; i++)
            {
                QualisysLists.add(new SinglepointList(splitLine[i]));
            }
            while(plotReader.hasNextLine())
            {
                splitLine = plotReader.nextLine().split("\t");
                for(int i = 0; i < QualisysLists.size();i++)
                {
                    QualisysLists.get(i).addTime(Double.parseDouble(splitLine[1]));
                    QualisysLists.get(i).addValue(Double.parseDouble(splitLine[i + 2]));
                }
            }
            
            
        }
        catch(FileNotFoundException ex)
        {}
    }
    
    public static void main(String [] args)
    {
        QualisysPack qP = new QualisysPack("C:/Users/Jonas/Dropbox/Java/utvev/Validata/Plot test/src/HeliTestvert.tsv");
        for(SinglepointList sPL:qP.QualisysLists)
        {
            System.out.println(sPL.getName());
            for(int i = 0;i < sPL.getTime().size();i++)
            {
                System.out.print(sPL.getTime().get(i) + "\t\t");
                System.out.print(sPL.getValue().get(i) + "\n");
            }
        }
    }
}
