package tarlog.eclipse.plugins.openwe;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.ObjectPluginAction;

public abstract class ResourceAction<RESOURCE extends IResource>  implements IObjectActionDelegate {

    protected ResourceAction() {
        super();
    }

    @SuppressWarnings("unchecked")
    public void run(IAction action) {
    	if (action instanceof ObjectPluginAction) {
    		ObjectPluginAction objectPluginAction = (ObjectPluginAction) action;
    		ISelection selection = objectPluginAction.getSelection();
    		if (selection instanceof TreeSelection) {
    			TreeSelection treeSelection = (TreeSelection) selection;
    			Object firstElement = treeSelection.getFirstElement();
    			if (firstElement instanceof IResource) {
    			    RESOURCE resource = (RESOURCE) firstElement;
    				doAction(resource);
    			}
    		}
    	}
    }
    
    protected abstract void doAction(RESOURCE resource);
    
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        // TODO Auto-generated method stub
        
    }

    public void selectionChanged(IAction action, ISelection selection) {
        // TODO Auto-generated method stub
        
    }


}