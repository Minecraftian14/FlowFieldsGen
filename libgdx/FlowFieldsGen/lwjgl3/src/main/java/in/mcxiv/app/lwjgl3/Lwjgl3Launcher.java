package in.mcxiv.app.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import in.mcxiv.app.FlowFieldsGen;

/**
 * Launches the desktop (LWJGL3) application.
 */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        createApplication();
    }

    public static final boolean forDebug = false;

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new FlowFieldsGen(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("FlowFieldsGen");
        configuration.setForegroundFPS(60);
        configuration.useVsync(true);
        if (forDebug) {
            configuration.setMaximized(true);
        } else {
            configuration.setResizable(false);
            configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
            configuration.setAutoIconify(true);
        }
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        configuration.setInitialBackgroundColor(Color.WHITE);
        return configuration;
    }
}