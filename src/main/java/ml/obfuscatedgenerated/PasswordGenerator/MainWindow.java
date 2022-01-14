package ml.obfuscatedgenerated.PasswordGenerator;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.*;

public class MainWindow implements Application {
    private Window window = null;
    private TextInput passGenOutput = null;
    private Map<String,Object> ns = null;

    @Override
    public void startup(Display display, Map<String, String> properties)
            throws Exception {
        BXMLSerializer bxmlSerializer = new BXMLSerializer();
        window = (Window)bxmlSerializer.readObject(MainWindow.class, "PassGen.bxml");
        window.open(display);
        ns = bxmlSerializer.getNamespace();
        passGenOutput = (TextInput) ns.get("passGenOutput");
        passGenOutput.setText("test!");
    }

    @Override
    public boolean shutdown(boolean optional) {
        if (window != null) {
            window.close();
        }

        return false;
    }

    @Override
    public void suspend() {
    }

    @Override
    public void resume() {
    }

    public static void main(String[] args) {
        DesktopApplicationContext.main(MainWindow.class, args);
    }
}