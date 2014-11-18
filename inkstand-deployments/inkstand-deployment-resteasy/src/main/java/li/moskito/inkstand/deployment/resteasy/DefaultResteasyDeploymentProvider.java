package li.moskito.inkstand.deployment.resteasy;

import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.ServletInfo;

import javax.annotation.Priority;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import li.moskito.inkstand.config.ApplicationConfiguration;
import li.moskito.inkstand.http.undertow.UndertowDeploymentProvider;

import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;

import ws.ament.hammock.core.impl.CDIListener;

@Singleton
@Priority(1)
public class DefaultResteasyDeploymentProvider implements UndertowDeploymentProvider {

    @Inject
    private ApplicationConfiguration appConfig;

    private DeploymentInfo deploymentInfo;

    @Override
    @Produces
    public DeploymentInfo getDeployment() {
        if (deploymentInfo == null) {
            final ResteasyDeployment deployment = new ResteasyDeployment();
            deployment.getActualResourceClasses().addAll(appConfig.getResourceClasses());
            deployment.getActualProviderClasses().addAll(appConfig.getProviderClasses());
            deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());

            final ListenerInfo listener = Servlets.listener(CDIListener.class);

            //@formatter:off
            final ServletInfo resteasyServlet = Servlets.servlet("ResteasyServlet", HttpServlet30Dispatcher.class)
                    .setAsyncSupported(true)
                    .setLoadOnStartup(1)
                    .addMapping("/*");

            deploymentInfo = new DeploymentInfo()
                    .addListener(listener)
                    .setContextPath(appConfig.getContextRoot())
                    .addServletContextAttribute(ResteasyDeployment.class.getName(), deployment)
                    .addServlet(resteasyServlet)
                    .setDeploymentName("ResteasyUndertow")
                    .setClassLoader(ClassLoader.getSystemClassLoader());
            // @formatter:on
        }
        return deploymentInfo;

    }
}
