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

import java.io.IOException;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.persistence.TemplatePersistenceData;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import tarlog.eclipse.plugins.Activator;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class TarlogPluginsPreferencePage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {

    public static final String OPEN_EXPLORER            = "openExplorer";
    public static final String RUN_SHELL                = "runShell";
    public static final String TEMPLATE_LOGGER_ID       = "tarlog-plugins.template.logger";
    public static final String COMMONS_LOGGING          = "commons-logging";
    public static final String SLF4J                    = "slf4j";
    public static final String LOGGER_NAME              = "loggerName";

    public static final String COMMONS_LOGGING_TEMPLATE = "${:import(org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory)}\n"
                                                                + "private static final Log logger = LogFactory.getLog(${enclosing_type}.class);";

    public static final String SLF4J_TEMPLATE           = "${:import(org.slf4j.Logger,org.slf4j.LoggerFactory)}\n"
                                                                + "private static final Logger logger = LoggerFactory.getLogger(${enclosing_type}.class);";

    public TarlogPluginsPreferencePage() {
        super(GRID);
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }

    /**
     * Creates the field editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences. Each field
     * editor knows how to save and restore itself.
     */
    public void createFieldEditors() {
        Composite parent = getFieldEditorParent();
        addField(new ComboFieldEditor(LOGGER_NAME, "Logger:", new String[][] { { "Slf4j", SLF4J },
                { "Commons Logging", COMMONS_LOGGING } }, parent));
        addField(new StringFieldEditor(RUN_SHELL, "Open Shell Command: ", parent));
        addField(new StringFieldEditor(OPEN_EXPLORER, "Open Explorer Command: ", parent));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
    }

    @Override
    public boolean performOk() {

        boolean okStatus = super.performOk();

        String loggerName = getPreferenceStore().getString(LOGGER_NAME);
        TemplateStore codeTemplateStore = Activator.getDefault().getCodeTemplateStore();
        TemplatePersistenceData loggerTemplateData = codeTemplateStore
                .getTemplateData(TEMPLATE_LOGGER_ID);
        if (loggerTemplateData != null) {
            codeTemplateStore.delete(loggerTemplateData);
        }
        if (loggerName.equals(COMMONS_LOGGING)) {
            addLogger(codeTemplateStore, COMMONS_LOGGING_TEMPLATE, "commons-loggins");
        } else if (loggerName.equals(SLF4J)) {
            addLogger(codeTemplateStore, SLF4J_TEMPLATE, "slf4j");
        } else {
            // should never happen
            throw new RuntimeException("Undefined logger");
        }
        try {
            codeTemplateStore.save();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return okStatus;

    }

    private void addLogger(TemplateStore codeTemplateStore, String template, String frameworkName) {
        codeTemplateStore.add(new TemplatePersistenceData(new Template("logger", "Adds logger for "
                + frameworkName, "java-members", template, true), true, TEMPLATE_LOGGER_ID));
    }
}
