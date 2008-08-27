package tarlog.eclipse.plugins.openwe;

import java.io.File;
import java.io.IOException;

/**
 * @author elman
 * 
 */
public class OpenCommandPrompt extends TreeSelectionAction {

    public OpenCommandPrompt() {
        super();
    }

    @Override
    protected void doAction(String path) {
        try {
            File file = new File(path);
            if (file.isFile()) {
                File parentFile = file.getParentFile();
                if (parentFile != null) {
                    path = parentFile.getAbsolutePath();
                }
            }
            Runtime.getRuntime().exec("cmd /c start cmd", null, new File(path));
            //Runtime.getRuntime().exec("cmd /c start cmd /k \"cd /d" + path + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
