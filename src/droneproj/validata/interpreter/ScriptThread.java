/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.interpreter;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 *
 * @author Victor
 */
public class ScriptThread extends Thread {
    private ClassLoader parent;
    private String script;
    private Object input;
    public ScriptThread(String args, Object input)
    {
        super(args);
        parent = getClass().getClassLoader();
        this.script = args;
        this.input = input;
    }
    
    @Override
    public void run()
    {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
        engine.eval(script);
        Invocable inv = (Invocable) engine;

        inv.invokeFunction("main", input);
        } catch(Exception e)
        {
            System.err.println(e.toString());
        }
        
    }
}
