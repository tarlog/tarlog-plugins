package tarlog.eclipse.plugins.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import tarlog.eclipse.plugins.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

    /*
     * (non-Javadoc)
     * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
     * initializeDefaultPreferences()
     */
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setDefault(TarlogPluginsPreferencePage.LOGGER_NAME,
                         TarlogPluginsPreferencePage.COMMONS_LOGGING);
    }

}
