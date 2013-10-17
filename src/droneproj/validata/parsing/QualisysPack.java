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
import java.util.Stack;

/**
 *
 * @author Jonas
 */
public class QualisysPack implements ListPackInterface{
    private ArrayList<SinglepointList> QualisysLists;
    
    public QualisysPack(String fileName, double multiplicator, double diff, boolean zeroOffsetCorrection)
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
                Stack<String> lineStack = new Stack<>();
                boolean trig = false;
                nextLine = plotReader.nextLine();
                lineStack.push(nextLine);
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
                    lineStack.push(nextLine);
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
                        int cnt = 0;
                        while(!lineStack.isEmpty() && cnt < 5){
                            nextLine = lineStack.pop();
                            splitLine = nextLine.split("\t");
                            offset = Double.parseDouble(splitLine[1]);
                            for(int i = 2; i < splitLine.length; i++)
                            {
                                QualisysLists.get(i-2).addTime(Double.parseDouble(splitLine[1])-offset);
                                QualisysLists.get(i-2).addValue(Double.parseDouble(splitLine[i]) * multiplicator);
                            }
                            cnt++;
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
            
                        
            //<editor-fold defaultstate="collapsed" desc="zero offset correction code">            
            if(zeroOffsetCorrection)
            {
                ArrayList<SinglepointList> tempPlaceholderLists = QualisysLists;
                QualisysLists = new ArrayList<>();
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
                    QualisysLists.add(new SinglepointList(eL.getName()));
                    for(int i = 0;i < eL.getSize();i++)
                    {
                        QualisysLists.get(cnt).addTime(eL.getTime(i));
                        QualisysLists.get(cnt).addValue(eL.getValue(i) + Math.abs(minValueUnderZero[cnt]));
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
    
    public static void main(String [] args)
    {
        QualisysPack qP = new QualisysPack("C:/Users/Jonas/Dropbox/Java/utvev/Validata/Plot test/src/Drop boll0517_500mm_b.tsv",1,20,true);
        for(SinglepointList sPL:qP.QualisysLists)
        {
            System.out.println("\n" + sPL.getName());
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
