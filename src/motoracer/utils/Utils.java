package motoracer.utils;

import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
    public static void executeScript(String scriptFileName) {
        try{
            FileReader fileReader = new FileReader("Scripts/"+scriptFileName);
            JsEngine.getJsEngine().engine.eval(fileReader);
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(scriptFileName + " not found " + e);
        } catch (ScriptException e) {
            System.out.println("ScriptException in " + scriptFileName + e);
        } catch (IOException e) {
            System.out.println("IO problem with " + scriptFileName + e);
        } catch (NullPointerException e){
            System.out.println ("Null pointer exception in " + scriptFileName + e);
        }
    }
    public static void executeAllScripts(){
        File folder = new File("Scripts/");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }
    }
}
