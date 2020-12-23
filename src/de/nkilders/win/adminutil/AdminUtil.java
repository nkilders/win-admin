package de.noah.win.adminutil;

import java.io.*;
import java.util.prefs.Preferences;

/**
 * Util for running Java applications as Admin in Windows
 *
 * @author  Noah Kilders
 */
public class AdminUtil {
    private static final File TEMP_FILE = new File(System.getProperty("java.io.tmpdir"), "WinAdminUtil.vbs");
    private static final File THIS_FILE = new File(AdminUtil.class
            .getProtectionDomain()
            .getCodeSource()
            .getLocation()
            .getPath()
            .substring(1)
            .replace("%20", " ")
            .replace("/", "\\"));

    /**
     * Runs the application again and asks for Admin rights.
     * Does not close ths running application.
     */
    public static void runAsAdmin() {
        if (isAdmin()) {
            TEMP_FILE.delete();
        } else {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(TEMP_FILE));
                writer.println("Set UAC = CreateObject(\"Shell.Application\")");
                writer.println("UAC.ShellExecute \"javaw.exe\", \"-jar \" & chr(34) & \"" + THIS_FILE.getPath().replace("\\", "\\\\") + "\" & chr(34), \"\", \"runas\", 0");
                writer.close();

                Runtime.getRuntime().exec("wscript " + TEMP_FILE.getPath());
                TEMP_FILE.deleteOnExit();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * @return {@code true} if the application was run as Admin
     */
    public static boolean isAdmin() {
        try {
            final Preferences preferences = Preferences.systemRoot();
            preferences.put("hEb9smh21s1kAM3T", "");
            preferences.remove("hEb9smh21s1kAM3T");
            preferences.flush();

            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}