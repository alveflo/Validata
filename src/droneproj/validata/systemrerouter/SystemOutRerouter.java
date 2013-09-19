/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.systemrerouter;
import javax.swing.JTextArea;
import droneproj.validata.utils.SystemRerouter;
import java.awt.Color;

/**
 *
 * @author Victor
 */
public class SystemOutRerouter extends SystemRerouter {
    public SystemOutRerouter(JTextArea textArea)
    {
        super(textArea);
    }

    @Override
    public void setFontColor() {
        JTextArea jTA = super.getOutTextArea();
        jTA.setForeground(Color.black);
        super.setOutTextArea(jTA);
    }

}
