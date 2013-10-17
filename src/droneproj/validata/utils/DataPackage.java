/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.utils;

import droneproj.validata.parsing.AcumenSinglePointPack;
import droneproj.validata.parsing.EnclosurePack;
import droneproj.validata.parsing.EnclosurePackNew;
import droneproj.validata.parsing.NavdataPack;
import droneproj.validata.parsing.QualisysPack;

/**
 *
 * @author Victor
 */
public class DataPackage {
    private String filename;
    private String name;
    private double multiplicator;
    private double diff;
    private boolean zeroOffsetCorrection;
    private ListPackInterface listPack;
    private int plotID;
    private Parser parser;
    
    /**
     * Constructs a Datapackage with said type and parameters
     * @param filename the file chosen
     * @param multiplicator multiplicator that should be applied on the samples, keep 1 to have the data unmodified
     * @param diff trigger for when the parser should start collecting datapoints, keep 0 to have the data unmodified
     * @param parser the parser that should be used on the file
     */
    public DataPackage(String filename, String multiplicator, String diff, boolean zeroOffsetCorrection,Parser parser)
    {
        this.filename = filename; 
        this.parser = parser;
        try{
            this.multiplicator = Double.parseDouble(multiplicator);
        }
        catch(NumberFormatException e)
        {
            this.multiplicator = 1.0;
            System.err.println(e.toString());
        }
        try{
            this.diff = Double.parseDouble(diff);
        }
        catch(NumberFormatException e)
        {
            this.diff = 0;
            System.err.println(e.toString());
        }
        this.zeroOffsetCorrection = zeroOffsetCorrection;
        String [] split = filename.split("\\\\");
        name = split[split.length-1];
        constructDataPack();
    }
    
    /**
     * Calls the parser selected in the constructor and parsers the file with said
     * trigger and multiplier
     */
    private void constructDataPack()
    {
        switch (this.parser)
        {
            case ACUMEN_ENCLOSURE:
                this.listPack = new EnclosurePack(this.filename, multiplicator,diff,zeroOffsetCorrection);
                break;
            case ACUMEN_ENCLOSURE_V2:
                this.listPack = new EnclosurePackNew(this.filename, multiplicator,diff,zeroOffsetCorrection);
                break;
            case ACUMEN_FLOATING_POINT:
                this.listPack = new AcumenSinglePointPack(this.filename, multiplicator,diff,zeroOffsetCorrection);
                break;
            case QUALISYS_MOTION_DATA:
                this.listPack = new QualisysPack(this.filename, multiplicator,diff,zeroOffsetCorrection);
                break;
            case NAVDATA:
                this.listPack = new NavdataPack(this.filename, multiplicator,diff,zeroOffsetCorrection);
                break;
            default:
                break;
        }
    }

    /**
     * @return the plotID
     */
    public int getPlotID() {
        return plotID;
    }

    /**
     * @param plotID the plotID to set
     */
    public void setPlotID(int plotID) {
        this.plotID = plotID;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the listPack
     */
    public ListPackInterface getListPack() {
        return listPack;
    }

    /**
     * @return the parser
     */
    public Parser getParser() {
        return parser;
    }
}
