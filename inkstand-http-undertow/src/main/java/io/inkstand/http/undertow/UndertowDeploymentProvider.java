/*
 * Copyright 2015 Gerald Muecke, gerald.muecke@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.inkstand.http.undertow;

import io.undertow.Undertow;
import io.undertow.servlet.api.DeploymentInfo;

/**
 * Interface to provide Undertow deployments that are injected and deployed automatically on {@link Undertow}.
 *
 * @author <a href="mailto:gerald@inkstand.io">Gerald M&uuml;cke</a>
 */
public interface UndertowDeploymentProvider {

    /**
     * Produces the deployment information required by the deployment manager
     *
     * @return
     *  a deployment information descriptor for the undertow server
     */
    DeploymentInfo getDeployment();
}
