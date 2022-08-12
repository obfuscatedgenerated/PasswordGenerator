package ml.obfuscatedgenerated.PasswordGenerator;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.*;
import org.apache.pivot.wtk.validation.IntRangeValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class MainWindow implements Application {
    private Window window = null;
    private Display activeDisplay = null;
    private TextInput passGenOutput = null;
    private final RandomGenerator gen = new RandomGenerator();
    private final XKCDGenerator xkcdgen = new XKCDGenerator();
    private TextInput lengthInput = null;
    private Dialog errBadInput = null;
    private BoxPane cboxPane = null;

    private final Runnable setSize = (() -> DesktopApplicationContext.sizeHostToFit(window));

    public MainWindow() throws IOException {
    }

    @Override
    public void startup(Display display, Map<String, String> properties)
            throws Exception {
        BXMLSerializer bxmlSerializer = new BXMLSerializer();
        window = (Window) bxmlSerializer.readObject(MainWindow.class, "PassGen.bxml");
        window.open(display);
        activeDisplay = display;
        Map<String, Object> ns = bxmlSerializer.getNamespace();
        passGenOutput = (TextInput) ns.get("passGenOutput");
        PushButton genButton = (PushButton) ns.get("genButton");
        genButton.getButtonPressListeners().add(mkPassListener);
        PushButton copyButton = (PushButton) ns.get("copyButton");
        copyButton.getButtonPressListeners().add(copyListener);
        lengthInput = (TextInput) ns.get("lengthInput");
        lengthInput.setValidator(new IntRangeValidator(1, 9999));
        errBadInput = (Dialog) bxmlSerializer.readObject(MainWindow.class, "ErrorBadInput.bxml");
        Button errCloseBtn = (Button) ns.get("errCloseBtn");
        errCloseBtn.getButtonPressListeners().add(closeDialogListener);
        cboxPane = (BoxPane) ns.get("cboxPane");
        for (Component i : cboxPane) {
            if (Objects.equals(i.getName(), "xkcd")) {
                continue;
            }
            ((Checkbox) i).press(); // toggle on all checkboxes
        }
        DesktopApplicationContext.scheduleRecurringCallback(setSize, 1);
    }

    private boolean validate() {
        List<String> includes = new ArrayList<>();
        for (Component i : cboxPane) {
            if (((Checkbox) i).isSelected()) {
                includes.add(((Checkbox) i).getName());
            }
        }
        boolean[] boolchecks = new boolean[]{
                lengthInput.isTextValid(),
                includes.size() > 0,
                true // add more validation booleans in place of this
        };
        return !Arrays.toString(boolchecks).contains("false");
    }


    private final ButtonPressListener mkPassListener = button -> {
        if (!validate()) {
            System.out.println("Validation error!");
            errBadInput.open(activeDisplay, window);
            return;
        }
        if (((Checkbox) cboxPane.getNamedComponent("xkcd")).isSelected()) {
            passGenOutput.setText(xkcdgen.generate(Integer.parseInt(lengthInput.getText()), "-")); // this parseInt shouldn't fail since input is validated
        } else {
            List<String> includes = new ArrayList<>();
            for (Component i : cboxPane) {
                if (((Checkbox) i).isSelected()) {
                    includes.add(i.getName());
                }
            }
            //List<String> includes = Arrays.asList("alpha", "upper", "numeral", "symbol");
            passGenOutput.setText(gen.generate(includes, Integer.parseInt(lengthInput.getText()))); // this parseInt shouldn't fail since input is validated
        }
    };

    private final ButtonPressListener copyListener = button -> {
        String content = passGenOutput.getText();
        java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(content), null);
        content = null; // imply the variable should be garbage collected
        System.gc();
    };

    private final ButtonPressListener closeDialogListener = button -> button.getWindow().close();

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
