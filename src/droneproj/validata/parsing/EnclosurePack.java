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
public class EnclosurePack implements ListPackInterface{
    private ArrayList<EnclosureList> enclousureLists;
    
    /**
     * Constructor that parses the file
     * @param fileName file to be parsed
     * @param multiplicator 
     * @param diff 
     */
    public EnclosurePack(String fileName,double multiplicator, double diff,boolean zeroOffsetCorrection){
        enclousureLists = new ArrayList<>();
        try{
            Scanner plotreader = new Scanner (new BufferedReader(new FileReader(fileName)));
            String headers = plotreader.nextLine();
            String [] plots = headers.split("\t");
            String [] points;
            double offset = 0;
            for(int i = 1; i < plots.length;i++)
            {
                enclousureLists.add(new EnclosureList(plots[i]));
            }
            
            //<editor-fold defaultstate="collapsed" desc="sample cropping code">
            if(diff != 0 && plotreader.hasNextLine())
            {
                Stack<String> lineStack = new Stack<>();
                boolean trig = false;
                headers = plotreader.nextLine();
                lineStack.push(headers);
                plots = headers.split("\t");
                double[] firstValues = new double[(plots.length - 1) * 2];
                
                /*Sample first values*/
                int cnt = 0;
                for(int i = 1 ; i < plots.length;i++)
                {
                    points = plots[i].replace("[", "").replace("]", "").split(",");
                    firstValues[cnt++] = Double.parseDouble(points[0]);
                    firstValues[cnt++] = Double.parseDouble(points[1]);
                }
                
                /*filter out unwanted values*/
                while(!trig && plotreader.hasNextLine())
                {
                    headers = plotreader.nextLine();
                    lineStack.push(headers);
                    plots = headers.split("\t");
                    cnt = 0;
                    for(int i = 1; i < plots.length;i++)
                    {
                        points = plots[i].replace("[", "").replace("]", "").split(",");
                        if(Math.abs((Double.parseDouble(points[0]) - firstValues[cnt++])) > diff ||
                               Math.abs((Double.parseDouble(points[1]) - firstValues[cnt++])) > diff)
                        {
                            trig = true;
                            break;
                        }
                    }
                    
                    if(trig)
                    {
                        cnt = 0;
                        while(!lineStack.isEmpty() && cnt < 5){
                            headers = lineStack.pop();
                            plots = headers.split("\t");
                            for(int i = 1; i < plots.length; i++)
                            {
                                points = plots[i].replace("[", "").replace("]", "").split(",");
                                offset = Double.parseDouble(plots[0]);
                                enclousureLists.get(i-1).addTime(Double.parseDouble(plots[0])-offset);
                                enclousureLists.get(i-1).addMin(Double.parseDouble(points[0]) * multiplicator);
                                enclousureLists.get(i-1).addMax(Double.parseDouble(points[1]) * multiplicator);
                            }
                            cnt++;
                        }
                    }
                }
            }
            //</editor-fold>
            
            while(plotreader.hasNextLine()){
                headers = plotreader.nextLine();
                plots = headers.split("\t");
                for(int i = 1; i < plots.length;i++)
                {
                    points = plots[i].replace("[", "").replace("]", "").split(",");
                    enclousureLists.get(i-1).addTime(Double.parseDouble(plots[0])-offset);
                    enclousureLists.get(i-1).addMin(Double.parseDouble(points[0]) * multiplicator);
                    enclousureLists.get(i-1).addMax(Double.parseDouble(points[1]) * multiplicator);
                }
            }
            
            
            //<editor-fold defaultstate="collapsed" desc="zero offset correction code">            
            if(zeroOffsetCorrection)
            {
                ArrayList<EnclosureList> tempPlaceholderLists = enclousureLists;
                enclousureLists = new ArrayList<>();
                double[] minValueUnderZero = new double[tempPlaceholderLists.size()];
                for(int i = 0;i < minValueUnderZero.length;i++)
                {minValueUnderZero[i] = 0;}
                int cnt = 0;
                for(EnclosureList eL: tempPlaceholderLists)
                {
                    for(int i = 0;i < eL.getSize();i++)
                    {
                        if(eL.getMin(i) < 0)
                        {
                            minValueUnderZero[cnt] = minValueUnderZero[cnt] > eL.getMin(i) ? eL.getMin(i) : minValueUnderZero[cnt];
                        }
                        eL.getMin(i);
                    }
                    cnt++;
                }
                cnt = 0;
                for(EnclosureList eL: tempPlaceholderLists)
                {
                    enclousureLists.add(new EnclosureList(eL.getName()));
                    for(int i = 0;i < eL.getSize();i++)
                    {
                        enclousureLists.get(cnt).addTime(eL.getTime(i));
                        enclousureLists.get(cnt).addMin(eL.getMin(i) + Math.abs(minValueUnderZero[cnt]));
                        enclousureLists.get(cnt).addMax(eL.getMax(i) + Math.abs(minValueUnderZero[cnt]));
                    }
                    cnt++;
                }
            }
                        //</editor-fold>
            
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
        EnclosurePack ab = new EnclosurePack("C:/Users/Jonas/Dropbox/Java/utvev/Validata/Plot test/src/Table",1,4,false);
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
