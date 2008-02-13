package tarlog.eclipse.plugins.openwe;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.eclipse.core.resources.IResource;

/**
 * @author melman
 * 
 */
public class OpenInWindowsExplorerAction extends ResourceAction<IResource> {

    public OpenInWindowsExplorerAction() {
        super();
    }

    @Override
    protected void doAction(IResource resource) {
        try {
            URI locationURI = resource.getLocationURI();
            File file = new File(locationURI);
            String absolutePath = file.getAbsolutePath();
            Runtime.getRuntime().exec("explorer ,/select, " + absolutePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
