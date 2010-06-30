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
