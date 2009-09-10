package tarlog.eclipse.plugins.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class IncreaseFontHandler extends AbstractHandler {

    public IncreaseFontHandler() {
    }

    public Object execute(ExecutionEvent event) throws ExecutionException {
        FontUtils.increaseFont();
        return null;
    }

}
