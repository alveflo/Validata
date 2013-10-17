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
    
    public EnclosurePackNew(String fileName, double multiplicator, double diff, boolean zeroOffsetCorrection){
        enclousureLists = new ArrayList<>();
        try{
            Scanner plotreader = new Scanner (new BufferedReader(new FileReader(fileName)));
            String headers = plotreader.nextLine();
            String [] plots = headers.split("\t");
            double offset = 0;
            for(int i = 1; i < plots.length;i = i +2)
            {
                enclousureLists.add(new EnclosureList(plots[i]));
            }            
            
            //<editor-fold defaultstate="collapsed" desc="sample cropping code">
            if(diff != 0 && plotreader.hasNextLine())
            {
                boolean trig = false;
                headers = plotreader.nextLine();
                plots = headers.replace(",",".").split("\t");
                double[] firstValues = new double[(plots.length - 1) * 2];
                int cnt = 0;
                
                /*Sample first values*/
                for(int i = 1 ; i < plots.length;i++)
                {
                    firstValues[i-1] = Double.parseDouble(plots[i]);
                }
                
                /*filter out unwanted values*/
                while(!trig && plotreader.hasNextLine())
                {
                    headers = plotreader.nextLine();
                    plots = headers.replace(",", ".").split("\t");
                    cnt = 0;
                    for(int i = 1; i < plots.length;i++)
                    {
                        if(Math.abs((Double.parseDouble(plots[i]) - firstValues[cnt++])) > diff ||
                               Math.abs((Double.parseDouble(plots[i]) - firstValues[cnt++])) > diff)
                        {
                            trig = true;
                            break;
                        }
                    }
                    
                    if(trig)
                    {
                        for(int i = 1; i < (plots.length/2)+1; i++)
                        {
                            offset = Double.parseDouble(plots[0]);
                            enclousureLists.get(i-1).addTime(Double.parseDouble(plots[0])-offset);
                            enclousureLists.get(i-1).addMin(Double.parseDouble(plots[i*2-1]) * multiplicator);
                            enclousureLists.get(i-1).addMax(Double.parseDouble(plots[i*2]) * multiplicator);
                        }
                    }
                }
            }
            //</editor-fold>
            
            while(plotreader.hasNextLine()){
                headers = plotreader.nextLine();
                headers = headers.replace(',', '.');
                plots = headers.split("\t");
                for(int i = 1; i < (plots.length/2)+1;i++)
                {
                    enclousureLists.get(i-1).addTime(Double.parseDouble(plots[0]));
                    enclousureLists.get(i-1).addMin(Double.parseDouble(plots[i*2-1]) * multiplicator);
                    enclousureLists.get(i-1).addMax(Double.parseDouble(plots[i*2])  * multiplicator);
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
        EnclosurePackNew ab = new EnclosurePackNew("C:\\Users\\Jonas\\Dropbox\\Utvecklingsprojekt\\Data\\Validering_Markör\\Acumen\\Acumen_Marker_50cm",1,0.003,true);
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

