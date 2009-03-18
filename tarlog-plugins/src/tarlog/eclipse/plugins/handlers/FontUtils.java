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
        IPreferencesService r = Platform.getPreferencesService();
        String value = r.getString(qualifier, key, null, null);
        FontData ary[] = PreferenceConverter.basicGetFontData(value);

        FontData fontdata = ary[0];
        fontdata.setHeight(fontdata.getHeight() + changeBy);
        Preferences n = r.getRootNode().node("/instance/" + qualifier);
        n.put(key, fontdata.toString());
        try {
            n.flush();
        } catch (BackingStoreException e) {
        }
    }

}
