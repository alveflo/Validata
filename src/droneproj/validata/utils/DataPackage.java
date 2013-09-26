/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.utils;

import droneproj.validata.parsing.AcumenSinglePointPack;
import droneproj.validata.parsing.EnclosurePack;
import droneproj.validata.parsing.NavdataPack;
import droneproj.validata.parsing.QualisysPack;

/**
 *
 * @author Victor
 */
public class DataPackage {
    private String filename;
    private String name;
    private ListPackInterface listPack;
    private int plotID;
    private Parser parser;
    
    public DataPackage(String filename, Parser parser)
    {
        this.filename = filename; 
        this.parser = parser;
        String [] split = filename.split("\\\\");
        name = split[split.length-1];
        constructDataPack();
    }
    
    private void constructDataPack()
    {
        switch (this.parser)
        {
            case ACUMEN_ENCLOSURE:
                this.listPack = new EnclosurePack(this.filename);
                break;
            case ACUMEN_FLOATING_POINT:
                this.listPack = new AcumenSinglePointPack(this.filename);
                break;
            case QUALISYS_MOTION_DATA:
                this.listPack = new QualisysPack(this.filename);
                break;
            case NAVDATA:
                //this.listPack = new NavdataPack(this.filename);
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
