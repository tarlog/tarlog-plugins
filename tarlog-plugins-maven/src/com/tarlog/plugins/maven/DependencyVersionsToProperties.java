package com.tarlog.plugins.maven;

import java.io.File;
import java.io.FileReader;
import java.net.URI;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class DependencyVersionsToProperties implements IObjectActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection treeSelection = (IStructuredSelection) selection;
			Object firstElement = treeSelection.getFirstElement();
			if (firstElement instanceof IResource) {
				handleResource((IResource) firstElement);
			}
		}

	}

	private void handleResource(IResource resource) {
		try {
			URI locationURI = resource.getLocationURI();
			File file = new File(locationURI);
			final Model model = new MavenXpp3Reader()
					.read(new FileReader(file));
			DependenciesDialog dialog = new DependenciesDialog(Activator
					.getActiveWorkbenchWindow().getShell(), model);
			dialog.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;

	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart workbenchPart) {
	}

}
