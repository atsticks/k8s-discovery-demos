package org.jacpfx.kube;

import javax.enterprise.inject.spi.Extension;
import org.jacpfx.discovery.extension.K8SExtension;
import org.jacpfx.kube.api.JaxRsActivator;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.undertow.WARArchive;

/**
 * Created by amo on 02.06.17.
 */
public class MainApp {
  public static void main(String[] args) throws Exception {
    System.out.println("START MAIN::");
    Swarm swarm = new Swarm();
    WARArchive deployment = ShrinkWrap.create(WARArchive.class);
    deployment.
        addAllDependencies().
        addClass(JaxRsActivator.class).
        addAsServiceProvider(Extension.class, K8SExtension.class).
        addPackages(true,"org.jacpfx");
    deployment.staticContent();
    swarm.start();
    swarm.deploy(deployment);
  }
}
