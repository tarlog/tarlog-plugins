package tarlog.eclispe.plugins.logme;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;


public class LogMeAction implements IObjectActionDelegate {

    public LogMeAction() {
        // TODO Auto-generated constructor stub
    }

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        System.out.println("LogMeAction.setActivePart()");
    }

    public void run(IAction action) {
        System.out.println("LogMeAction.setActivePart()");
    }

    public void selectionChanged(IAction action, ISelection selection) {
        System.out.println("LogMeAction.setActivePart()");
    }

}
