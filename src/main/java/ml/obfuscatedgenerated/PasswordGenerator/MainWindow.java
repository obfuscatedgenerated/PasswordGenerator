package ml.obfuscatedgenerated.PasswordGenerator;

import com.sun.tools.javac.Main;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.*;
import org.apache.pivot.wtk.validation.IntRangeValidator;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainWindow implements Application {
    private Window window = null;
    private Display activeDisplay = null;
    private TextInput passGenOutput = null;
    private final Generator gen = new Generator();
    private TextInput lengthInput = null;
    private Dialog errBadLength = null;
    private Button errCloseBtn = null;

    private final Runnable setSize = (() -> DesktopApplicationContext.sizeHostToFit(window));

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
        lengthInput.setValidator(new IntRangeValidator(1,9999));
        errBadLength = (Dialog) bxmlSerializer.readObject(MainWindow.class, "ErrorBadLength.bxml");
        errCloseBtn = (Button) ns.get("errCloseBtn");
        errCloseBtn.getButtonPressListeners().add(closeDialogListener);
        DesktopApplicationContext.scheduleRecurringCallback(setSize, 1);
    }

    private boolean validate () {
        boolean[] boolchecks = new boolean[]{
                lengthInput.isTextValid(),
                true // add more validation booleans in place of this
        };
        return !Arrays.toString(boolchecks).contains("false");
    }

    private void playsound(String path) {
        File sound_f = new File(path);
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound_f));
            clip.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void playresource(String resourcePath) {
        InputStream sound_f = getClass().getResourceAsStream(resourcePath);
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(sound_f)));
            clip.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private final ButtonPressListener mkPassListener = button -> {
        // TODO: user options (length [DONE], structure)
        // TODO: xkcd mode (with custom delimiters)
        // TODO: chunked mode with custom delimiters (e.g. 12345_abcde_@}{:?)
        if (!validate()) {
            System.out.println("Validation error!");
            playresource("error.wav");
            errBadLength.open(activeDisplay,window);
            return;
        }
        List<String> includes = Arrays.asList("alpha", "upper", "numeral", "symbol");
        passGenOutput.setText(gen.generate(includes, Integer.parseInt(lengthInput.getText()))); // this parseInt shouldn't fail since input is validated
    };

    private final ButtonPressListener copyListener = button -> {
        String content = passGenOutput.getText();
        java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(content), null);
        content = null; // imply the variable should be garbage collected
        System.gc();
    };

    private final ButtonPressListener closeDialogListener = button -> {
        button.getWindow().close();
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
