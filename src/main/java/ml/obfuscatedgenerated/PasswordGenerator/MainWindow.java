package ml.obfuscatedgenerated.PasswordGenerator;

import java.awt.Color;
import java.awt.Font;

import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.*;

public class MainWindow implements Application {
    private Window window = null;

    @Override
    public void startup(Display display, Map<String, String> properties) {
        window = new Window();

        Label label = new Label();
        label.setText("Hello World!");
        label.getStyles().put("font", new Font("Arial", Font.BOLD, 24));
        label.getStyles().put("color", Color.RED);
        label.getStyles().put("horizontalAlignment",
                HorizontalAlignment.CENTER);
        label.getStyles().put("verticalAlignment",
                VerticalAlignment.CENTER);

        window.setContent(label);
        window.setTitle("Hello World!");
        window.setMaximized(true);

        window.open(display);
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