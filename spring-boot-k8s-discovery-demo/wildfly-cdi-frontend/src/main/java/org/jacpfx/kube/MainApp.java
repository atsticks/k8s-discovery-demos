package org.jacpfx.kube;

import org.jacpfx.kube.controller.FrontendController;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.undertow.WARArchive;

/**
 * Created by amo on 02.06.17.
 */
public class MainApp {
  public static void main(String[] args) throws Exception {
    Swarm swarm = new Swarm();
    WARArchive deployment = ShrinkWrap.create(WARArchive.class);
    deployment.addAllDependencies().addClass(JaxRsActivator.class).
        addPackage("org.jacpfx.kube.controller").
        addPackage("org.jacpfx.kube").
        addPackage("org.jacpfx.kube.service").
        addPackage("org.jacpfx.kube.domain").
        addPackage("org.jacpfx.discovery.extension");
    swarm.start();
    swarm.deploy(deployment);
  }
}
