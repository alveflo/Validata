/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package droneproj.validata.interpreter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        parseAPI();
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
        engine.eval(script);
        Invocable inv = (Invocable) engine;

        inv.invokeFunction("main");
        } catch(Exception e)
        {
            System.err.println(e.toString());
        }
    }
    
    private void parseAPI() {
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader (ScriptThread.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "API.js"));
            String line = null;
            StringBuilder  stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");

            while( ( line = reader.readLine() ) != null ) {
                stringBuilder.append( line );
                stringBuilder.append( ls );
            }
            script = stringBuilder.toString() + script;
        
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }
    }
    
}
