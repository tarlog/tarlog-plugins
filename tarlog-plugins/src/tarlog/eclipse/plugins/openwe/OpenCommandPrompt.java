/**
 * 
 */
package tarlog.eclipse.plugins.openwe;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;

/**
 * @author elman
 * 
 */
public class OpenCommandPrompt extends ResourceAction<IContainer> {

    public OpenCommandPrompt() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see tarlog.eclipse.plugins.openwe.OpenBaseAction#doAction(java.lang.String)
     */
    @Override
    protected void doAction(IContainer container) {
        try {
            URI locationURI = container.getLocationURI();
            File file = new File(locationURI);
            String absolutePath = file.getAbsolutePath();
            Process process = Runtime.getRuntime().exec("cmd /k start cmd /k \"cd " + absolutePath + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
