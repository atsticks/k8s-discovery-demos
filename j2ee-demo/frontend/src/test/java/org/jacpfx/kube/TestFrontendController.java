package org.jacpfx.kube;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.jacpfx.kube.controller.FrontendController;
import org.jacpfx.kube.controller.MyApplication;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ClassAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * @author <a href="mailto:mjobanek@redhat.com">Matous Jobanek</a>
 */
@RunWith(Arquillian.class)
public class TestFrontendController {

    @EJB
    private PersonService service;

    @Deployment(name="web")
    public static Archive deployWar(){
        return ShrinkWrap.create(WebArchive.class)
                .add(new ClassAsset(TestFrontendController.class),
                        TestFrontendController.class.getName())
                .add(new ClassAsset(FrontendController.class),
                        FrontendController.class.getName())
                .add(new ClassAsset(MyApplication.class),
                        MyApplication.class.getName());

    }

    @Deployment(name="services")
    public static Archive deployEJB(){
        return ShrinkWrap.createFromZipFile(JavaArchive.class,
                new File("../services/target/person-services-1.0-SNAPSHOT.jar"));
    }

    @Test
    public void readUsers() throws IOException {
        System.out.println("=========================================");
        System.out.println("Persons registered: ");
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://localhost:8080/person-web/api/users");
        request.addHeader(new BasicHeader("Accept", MediaType.APPLICATION_JSON));
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (
                new InputStreamReader(response.getEntity().getContent()));
        String line;
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
        System.out.println("=========================================");

    }
}