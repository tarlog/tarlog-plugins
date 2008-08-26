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
        Process process = null;
        try {
            File file = new File(path);
            if (file.isFile()) {
                File parentFile = file.getParentFile();
                if (parentFile != null) {
                    path = parentFile.getAbsolutePath();
                }
            }
            process = Runtime.getRuntime().exec(
                "cmd /k start cmd /k \"cd /d" + path + "\"");
            Thread.sleep(500);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                // we don't really care for the exit value
                // what's important here, to make sure that the first cmd process was terminated
                process.exitValue();
            } catch (IllegalThreadStateException e) {
                // the process was not yet terminated
                process.destroy();
            }
        }
    }

}
