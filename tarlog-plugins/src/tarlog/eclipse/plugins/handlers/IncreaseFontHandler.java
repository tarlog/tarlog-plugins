package tarlog.eclipse.plugins.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class IncreaseFontHandler extends AbstractHandler {

    public IncreaseFontHandler() {
    }

    public Object execute(ExecutionEvent event) throws ExecutionException {

//        IWorkbench workbench = PlatformUI.getWorkbench();
//        
//        Display display = workbench.getDisplay();
//        display.addListener(SWT.MouseWheel, new Listener() {
//
//            public void handleEvent(Event arg0) {
//                System.out.println(".handleEvent()");
//                
//            }});
//        IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
//        if (activeWorkbenchWindow == null) {
//            return null;
//        }
//        IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
//        if (activePage == null) {
//            return null;
//        }
//        IEditorPart activeEditor = activePage.getActiveEditor();
//        if (activeEditor == null) {
//            return null;
//        }

        FontUtils.increaseFont();
        return null;
    }

}
