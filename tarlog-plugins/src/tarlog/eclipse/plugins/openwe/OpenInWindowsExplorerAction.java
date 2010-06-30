/*******************************************************************************
 *   Copyright 2008,2010 Michael Elman (aka tarlog - http://tarlogonjava.blogspot.com)
 *                                                                                    
 * Licensed under the Apache License, Version 2.0 (the "License");                    
 * you may not use this file except in compliance with the License.                   
 * You may obtain a copy of the License at                                            
 *                                                                                    
 *    http://www.apache.org/licenses/LICENSE-2.0                                      
 *                                                                                    
 * Unless required by applicable law or agreed to in writing, software                
 * distributed under the License is distributed on an "AS IS" BASIS,                  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.           
 * See the License for the specific language governing permissions and                
 * limitations under the License.                                                     
 *******************************************************************************/
package tarlog.eclipse.plugins.openwe;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import tarlog.eclipse.plugins.Activator;
import tarlog.eclipse.plugins.preferences.TarlogPluginsPreferencePage;

/**
 * @author melman
 */
public class OpenInWindowsExplorerAction extends TreeSelectionAction implements
        IPropertyChangeListener {

    private String command = null;

    public OpenInWindowsExplorerAction() {
        super();
        IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
        command = preferenceStore.getString(TarlogPluginsPreferencePage.OPEN_EXPLORER);
        preferenceStore.addPropertyChangeListener(this);
    }

    @Override
    protected void doAction(String path) {
        try {
            Runtime.getRuntime().exec(MessageFormat.format(command, path));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getProperty().equals(TarlogPluginsPreferencePage.OPEN_EXPLORER)) {
            Object newValue = event.getNewValue();
            if (newValue instanceof String) {
                command = (String) newValue;
            }
        }
    }

}
