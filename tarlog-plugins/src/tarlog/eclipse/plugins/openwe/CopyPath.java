package tarlog.eclipse.plugins.openwe;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.IWorkbench;

import tarlog.eclipse.plugins.Activator;

public class CopyPath extends TreeSelectionAction {

    @Override
    protected void doAction(String path) {
        IWorkbench workbench = Activator.getDefault().getWorkbench();

        Clipboard clipboard = new Clipboard(workbench.getDisplay());
        TextTransfer textTransfer = TextTransfer.getInstance();
        clipboard.setContents(new Object[] {path}, new Transfer[] {textTransfer});

    }

}
