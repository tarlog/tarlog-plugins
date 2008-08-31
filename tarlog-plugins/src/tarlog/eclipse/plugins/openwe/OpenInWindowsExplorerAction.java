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
