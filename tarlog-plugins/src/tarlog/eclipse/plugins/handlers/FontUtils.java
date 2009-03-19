package tarlog.eclipse.plugins.handlers;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.FontData;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

class FontUtils {

    static void increaseFont() {
        changeFont(1);
    }

    static void decreaseFont() {
        changeFont(-1);
    }

    private static void changeFont(int changeBy) {
        changeFont("org.eclipse.ui.workbench", "org.eclipse.jdt.ui.editors.textfont", changeBy);
        changeFont("org.eclipse.ui.workbench", "org.eclipse.jface.textfont", changeBy);
    }

    private static void changeFont(String qualifier, String key, int changeBy) {
        IPreferencesService preferencesService = Platform.getPreferencesService();
        String value = preferencesService.getString(qualifier, key, null, null);
        FontData fontData[] = PreferenceConverter.basicGetFontData(value);

        FontData fontdata = fontData[0];
        fontdata.setHeight(fontdata.getHeight() + changeBy);
        Preferences preferences = preferencesService.getRootNode().node("/instance/" + qualifier);
        preferences.put(key, fontdata.toString());
        try {
            preferences.flush();
        } catch (BackingStoreException e) {
        }
    }

}
