package com.tarlog.plugins.maven;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
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
			if (dialog.open() == Dialog.OK) {
				// create backup
				FileUtils.copyFile(file, new File(file.getAbsolutePath() + ".orig"));
				new MavenXpp3Writer().write(new FileWriter(file), model);
			}
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
