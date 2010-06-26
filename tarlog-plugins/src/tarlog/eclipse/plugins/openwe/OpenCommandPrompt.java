/**
 *    Copyright 2008 Michael Elman (aka tarlog - http://tarlogonjava.blogspot.com) 
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package tarlog.eclipse.plugins.openwe;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import tarlog.eclipse.plugins.Activator;
import tarlog.eclipse.plugins.preferences.TarlogPluginsPreferencePage;

/**
 * @author elman
 */
public class OpenCommandPrompt extends TreeSelectionAction implements IPropertyChangeListener {

    private String command = null;

    public OpenCommandPrompt() {
        super();
        IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
        command = preferenceStore.getString(TarlogPluginsPreferencePage.RUN_SHELL);
        preferenceStore.addPropertyChangeListener(this);
    }

    @Override
    protected void doAction(String path) {
        try {
            File file = new File(path);
            if (file.isFile()) {
                File parentFile = file.getParentFile();
                if (parentFile != null) {
                    path = parentFile.getAbsolutePath();
                }
            }
            Runtime.getRuntime().exec(command, null, new File(path));
            // Runtime.getRuntime().exec("cmd /c start cmd /k \"cd /d" + path +
            // "\"");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getProperty().equals(TarlogPluginsPreferencePage.RUN_SHELL)) {
            Object newValue = event.getNewValue();
            if (newValue instanceof String) {
                command = (String) newValue;
            }
        }
    }

}
