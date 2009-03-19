package tarlog.eclipse.plugins;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class StartUp implements IStartup {

    public void earlyStartup() {
        System.out.println("StartUp.earlyStartup()");
        IWorkbench workbench = PlatformUI.getWorkbench();

        workbench.addWindowListener(new IWindowListener() {

            public void windowActivated(IWorkbenchWindow iworkbenchwindow) {
                System.out.println(".windowActivated() " + String.valueOf(iworkbenchwindow));
                Shell shell = iworkbenchwindow.getShell();
                if (shell != null) {
                    shell.addMouseWheelListener(new MouseWheelListener() {

                        public void mouseScrolled(MouseEvent mouseevent) {
                            System.out.println(mouseevent.count);
                        }

                    });
                }
            }

            public void windowClosed(IWorkbenchWindow iworkbenchwindow) {
                // TODO Auto-generated method stub

            }

            public void windowDeactivated(IWorkbenchWindow iworkbenchwindow) {
                // TODO Auto-generated method stub

            }

            public void windowOpened(IWorkbenchWindow iworkbenchwindow) {
                System.out.println(".windowOpened() " + String.valueOf(iworkbenchwindow));

            }
        });

        Display display = workbench.getDisplay();

        display.addFilter(SWT.KeyDown, new Listener() {

            public void mouseScrolled(MouseEvent mouseevent) {
                System.out.println(mouseevent.count);
            }

            public void handleEvent(Event event) {
                System.out.println(".handleEvent()");

            }
        });

    }
}
