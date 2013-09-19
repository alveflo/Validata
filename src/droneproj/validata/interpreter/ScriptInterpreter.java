/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.interpreter;
import droneproj.validata.utils.GroovyInterface;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class ScriptInterpreter {
    // Singleton pattern - The one and only instance...
    private static ScriptInterpreter theInstance = new ScriptInterpreter();
    private ClassLoader parent;
    // Implement scriptthread- run concurrently
    private Thread scriptThread;
    
    protected ScriptInterpreter()
    {
        parent = getClass().getClassLoader();
    }
    /**
     * Returns the ScriptInterpreter
     * @return 
     */
    public static ScriptInterpreter getInstance() 
    {
        return theInstance;
    }
    
    /**
     * Interprets and runs the specified script
     * @param script The complete script
     */
    public void runScript(String script)
    {
        GroovyClassLoader gcl = new GroovyClassLoader(this.parent);
        Class theClass = gcl.parseClass(script);
        GroovyObject groovyObj = null;        
        try
        {
            groovyObj = (GroovyObject)theClass.newInstance();
            GroovyInterface obj = (GroovyInterface) groovyObj;
            obj.run(null);
        } catch (Exception e)
        {
            System.err.println(e.toString());
        }
    }
    
}
