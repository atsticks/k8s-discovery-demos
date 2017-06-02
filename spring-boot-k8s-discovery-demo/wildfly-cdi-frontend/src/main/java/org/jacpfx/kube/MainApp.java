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
    JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
    deployment.addAllDependencies().addPackage("org.jacpfx.kube.controller");
    swarm.start();
    swarm.deploy(deployment);
  }
}
