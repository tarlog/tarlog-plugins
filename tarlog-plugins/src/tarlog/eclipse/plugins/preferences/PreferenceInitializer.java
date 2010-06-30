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
     * 
     * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
     * initializeDefaultPreferences()
     */
    public void initializeDefaultPreferences() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.setDefault(TarlogPluginsPreferencePage.LOGGER_NAME, TarlogPluginsPreferencePage.SLF4J);
        store.setDefault(TarlogPluginsPreferencePage.RUN_SHELL, "cmd /c start cmd");
        store.setDefault(TarlogPluginsPreferencePage.OPEN_EXPLORER, "explorer /select,  {0}");
    }

}
