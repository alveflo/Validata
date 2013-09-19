/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.interpreter;

/**
 *
 * @author Victor
 */
public class ScriptInterpreter {
    // Singleton pattern - The one and only instance...
    private static ScriptInterpreter theInstance = new ScriptInterpreter();
    
    protected ScriptInterpreter()
    {
        
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
        new ScriptThread(script, "hello").start();
    }
    
}
