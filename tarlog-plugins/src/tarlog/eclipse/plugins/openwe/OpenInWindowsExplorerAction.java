package tarlog.eclipse.plugins.openwe;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.ObjectPluginAction;


/**
 * @author melman
 *
 */
public class OpenInWindowsExplorerAction implements IObjectActionDelegate {


	public OpenInWindowsExplorerAction() {
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}

	public void run(IAction action) {
		if (action instanceof ObjectPluginAction) {
			ObjectPluginAction objectPluginAction = (ObjectPluginAction) action;
			ISelection selection = objectPluginAction.getSelection();
			if (selection instanceof TreeSelection) {
				TreeSelection treeSelection = (TreeSelection) selection;
				Object firstElement = treeSelection.getFirstElement();
				if (firstElement instanceof IResource) {
					IResource resource = (IResource) firstElement;
					URI locationURI = resource.getLocationURI();
					File file = new File(locationURI);
					String absolutePath = file.getAbsolutePath();
					try {
						Runtime.getRuntime().exec("explorer ,/select, " + absolutePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}
}
