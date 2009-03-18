package tarlog.eclipse.plugins.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class DecreaseFontHandler extends AbstractHandler {

    public Object execute(ExecutionEvent arg0) throws ExecutionException {
        FontUtils.decreaseFont();
        return null;
    }

}
