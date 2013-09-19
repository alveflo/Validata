/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Victor
 */
public abstract class SystemRerouter extends OutputStream {
    private javax.swing.JTextArea outTextArea;
    private Queue<Byte> dataQueue;
    /**
     * Creates a new System.out.* rerouter.
     * Used for logging/message purposes in GUI.
     * @param textArea The JTextArea to be attached to System.out.*
     */
    public SystemRerouter(javax.swing.JTextArea textArea)
    {
        this.outTextArea = textArea;
        this.dataQueue = new LinkedList<Byte>();
    }
    
    /**
     * Prints data to JTextField component 
     */
    private void handleIncomingData()
    {
        byte b;
        StringBuilder strBuilder = new StringBuilder();
        setFontColor();
        while (!dataQueue.isEmpty()) {
            b = dataQueue.poll();
            strBuilder.append((char)b);
        }        
        getOutTextArea().append(strBuilder.toString());
    }
    
    /**
     * Specifies color for printing
     */
    public abstract void setFontColor();
    
    @Override
    public void write(int b) throws IOException {
//        dataList.add((byte) b);
        dataQueue.add((byte)b);
        handleIncomingData();
    }

    //<editor-fold defaultstate="collapsed" desc="Private member encapsulators">

    //</editor-fold>

    /**
     * @return the outTextArea
     */
    public javax.swing.JTextArea getOutTextArea() {
        return outTextArea;
    }

    /**
     * @param outTextArea the outTextArea to set
     */
    public void setOutTextArea(javax.swing.JTextArea outTextArea) {
        this.outTextArea = outTextArea;
    }
}
