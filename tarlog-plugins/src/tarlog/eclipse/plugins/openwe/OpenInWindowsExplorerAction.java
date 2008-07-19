package tarlog.eclipse.plugins.openwe;

import java.io.IOException;

/**
 * @author melman
 * 
 */
public class OpenInWindowsExplorerAction extends TreeSelectionAction {

    public OpenInWindowsExplorerAction() {
        super();
    }

    @Override
    protected void doAction(String path) {
        try {
            Runtime.getRuntime().exec("explorer ,/select, " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
