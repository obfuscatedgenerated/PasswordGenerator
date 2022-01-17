package ml.obfuscatedgenerated.PasswordGenerator;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.*;

import java.util.Arrays;
import java.util.List;

public class MainWindow implements Application {
    private Window window = null;
    private TextInput passGenOutput = null;
    private final Generator gen = new Generator();

    private final Runnable setSize = (() -> DesktopApplicationContext.sizeHostToFit(window));

    @Override
    public void startup(Display display, Map<String, String> properties)
            throws Exception {
        BXMLSerializer bxmlSerializer = new BXMLSerializer();
        window = (Window) bxmlSerializer.readObject(MainWindow.class, "PassGen.bxml");
        window.open(display);
        Map<String, Object> ns = bxmlSerializer.getNamespace();
        passGenOutput = (TextInput) ns.get("passGenOutput");
        PushButton genButton = (PushButton) ns.get("genButton");
        genButton.getButtonPressListeners().add(mkPassListener);
        PushButton copyButton = (PushButton) ns.get("copyButton");
        copyButton.getButtonPressListeners().add(copyListener);
        DesktopApplicationContext.scheduleRecurringCallback(setSize, 1);
    }

    private final ButtonPressListener mkPassListener = button -> {
        // TODO: user options (length, structure)
        // TODO: xkcd mode (with custom delimiters)
        // TODO: chunked mode with custom delimiters (e.g. 12345_abcde_@}{:?)
        List<String> includes = Arrays.asList("alpha", "upper", "numeral", "symbol");
        passGenOutput.setText(gen.generate(includes, 16));
    };

    private final ButtonPressListener copyListener = button -> {
        String content = passGenOutput.getText();
        java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(content), null);
        content = null; // imply the variable should be garbage collected
        System.gc();
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
        System.setProperty("apple.awt.application.name", "Password Generator");
        DesktopApplicationContext.main(MainWindow.class, args);
    }
}
