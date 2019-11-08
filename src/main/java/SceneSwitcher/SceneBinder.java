package SceneSwitcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for binding a key to a fxml document path, or a Class based view that inherits from Node
 * Author: Øyvind Johannessen
 * Version 0.1
 */
public class SceneBinder<T extends Parent> {
    private Map<String, T> bindings;

    /**
     * Protected constructor so it can only be used within its package
     */
    protected SceneBinder() {
        bindings = new HashMap<>();
    }

    /**
     * Returns a parent referenced by the input key
     * @param key The key that points to the view
     * @return A class that extends Parent (view)
     */
    protected T getView(String key) {
        return bindings.get(key);
    }

    /**
     *
     * @param views An array of classes that extends class Parent
     * @param keys An array of keys that match index order of views array
     */
    public void bindListOfViews(T[] views, String[] keys) {
        if(views.length != keys.length)
            throw new IllegalArgumentException("Views array and keys array must have equal length");
        for(int i = 0; i < keys.length; i++) {
            bind(keys[i], views[i]);
        }
    }

    /**
     *
     * @param directoryName The name/path to the directory which contains all the fxml documents
     * @param keys A list of keys that match index order from top being 0, to the last fxml file.
     */
    public void bindDirectory(String directoryName, String[] keys) {
        File[] files = getFilesInDirectory(directoryName);
        if(files.length != keys.length)
            throw new IllegalArgumentException(directoryName + " has " + files.length + " files and keys array has " + keys.length + " entries. Length has to be equal.");
        for(int i = 0; i < keys.length; i++) {
            bind(keys[i], directoryName + "/" + files[i].getName());
        }
    }

    /**
     * Private method that returns a list of files contained in a directory
     * @param directoryName The name of the directory that contains the files
     */
    private File[] getFilesInDirectory(String directoryName) {
        ClassLoader loader = getClass().getClassLoader();
        URL url = loader.getResource(directoryName);
        String path = url.getPath();
        return new File(path).listFiles();
    }

    /**
     * Binds a key and a class that extends Parent
     * @param key The key that should point to the Parent
     * @param view The Parent view
     */
    public void bind(String key, T view) {
        if(bindings.containsKey(key))
            throw new IllegalArgumentException("The key provided already exists, choose another one");
        else {
            if(bindings.containsValue(view))
                printDuplicateWarning();
            bindings.put(key, view);
        }
    }

    private void printDuplicateWarning() {
        System.err.println("Warning: Found duplicate value in SceneBinder. This means there are two different keys pointing to the same value");
    }

    /**
     * Binds a key and a fxml document
     * @param key The key that should point to the document
     * @param fxmlDocName The path to the fxml document
     */
    public void bind(String key, String fxmlDocName) {
        if(bindings.containsKey(key))
            throw new IllegalArgumentException("The key provided already exists, choose another one");
        else {
            try {
                T view = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlDocName));
                if(bindings.containsValue(view))
                    printDuplicateWarning();
                bindings.put(key, view);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
