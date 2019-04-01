package a2.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class JsEngine {
    private static JsEngine single_instance = null;
    public ScriptEngine engine;
    private JsEngine() {
        engine = new ScriptEngineManager().getEngineByName("js");
    }
    public static JsEngine getJsEngine() {
        if(single_instance==null){
            single_instance = new JsEngine();
        }
        return single_instance;
    }
}
