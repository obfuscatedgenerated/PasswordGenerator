package ml.obfuscatedgenerated.PasswordGenerator;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.*;

import java.util.Arrays;
import java.util.List;

public class MainWindow implements Application {
    private Window window = null;
    private TextInput passGenOutput = null;
    private PushButton genButton = null;
    private Map<String, Object> ns = null;
    private Generator gen = new Generator();

    @Override
    public void startup(Display display, Map<String, String> properties)
            throws Exception {
        BXMLSerializer bxmlSerializer = new BXMLSerializer();
        window = (Window) bxmlSerializer.readObject(MainWindow.class, "PassGen.bxml");
        window.open(display);
        ns = bxmlSerializer.getNamespace();
        passGenOutput = (TextInput) ns.get("passGenOutput");
        genButton = (PushButton) ns.get("genButton");
        genButton.getButtonPressListeners().add(mkPassListener);
    }

    ButtonPressListener mkPassListener = button -> {
        // TODO: user options (length, structure)
        List<String> includes = Arrays.asList("alpha", "upper", "numeral", "symbol");
        passGenOutput.setText(gen.generate(includes, 16));
    };

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