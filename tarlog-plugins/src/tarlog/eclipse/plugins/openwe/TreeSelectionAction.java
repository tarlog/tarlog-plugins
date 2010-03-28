/**
 *    Copyright 2008 Michael Elman (aka tarlog - http://tarlogonjava.blogspot.com) 
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package tarlog.eclipse.plugins.openwe;

import java.io.File;
import java.net.URI;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public abstract class TreeSelectionAction implements IObjectActionDelegate {

    private ISelection selection;

    protected TreeSelectionAction() {
        super();
    }

    public void run(IAction action) {
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection treeSelection = (IStructuredSelection)selection;
            Object firstElement = treeSelection.getFirstElement();
            if (firstElement instanceof IResource) {
                handleResource((IResource)firstElement);
            } else if (firstElement instanceof IJavaElement) {
                IJavaElement javaElement = (IJavaElement)firstElement;
                handleJavaElement(javaElement);
            }
        }
    }

    private void handleJavaElement(IJavaElement javaElement) {
        IResource resource = javaElement.getResource();
        if (resource != null) {
            handleResource(resource);
        } else {
            String path = javaElement.getPath().toOSString();
            doAction(path);
        }
    }

    protected void handleResource(IResource resource) {
        URI locationURI = resource.getLocationURI();
        File file = new File(locationURI);
        String absolutePath = file.getAbsolutePath();
        doAction(absolutePath);
    }

    protected abstract void doAction(String path);

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {

    }

    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;

    }

}
