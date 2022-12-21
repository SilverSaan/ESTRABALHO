/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pt.estg.es.web;

import net.sourceforge.jwebunit.api.IElement;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.Testable;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.protocol.servlet.arq514hack.descriptors.api.application.ApplicationDescriptor;
import pt.estg.es.model.Usuario;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.DescriptorImporter;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.junit.Test;
import org.junit.runner.RunWith;
import pt.estgp.es.services.usuarioService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.*;
import java.util.List;
import java.util.logging.Logger;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class FormStrutsIT
{

    //Configuração do Arquilian para adicionar os recursos da Arquitetura EE

    //Diferentes formas de criar um deployment https://github.com/nuzayats/arquillian-ear/blob/master/ear/src/test/java/org/example/EarFromZipFileIT.java
    @Deployment
    public static Archive<?> createDeploymentPackage() throws IOException {
        final String testWarName = "test.war";

        // Build the ear with Maven by hand before run the test!
        final EnterpriseArchive ear = ShrinkWrap.createFromZipFile(
                EnterpriseArchive.class, new File("target/kitchensink-ear.ear"));

        System.out.println("---ear---");
        addTestWar2EAR(ear, FormStrutsIT.class, testWarName);
        System.out.println(ear.toString(true));

        System.out.println();
        System.out.println("---war---");
        final WebArchive war = ear.getAsType(WebArchive.class, testWarName);
        System.out.println(war.toString(true));

        return ear;
    }

    /**
     * Adicionar uma aplicação apenas para o TESTE, ou seja a nossa aplicação EAR passa a ter um
     * EAR com um web.war um ejb.jar e agora mais um WAR que só tem a classe de testes e que poderá
     * testar a aplicação como um todo
     * @param ear
     * @param testClass
     * @param testWarName
     * @throws IOException
     */
    private static void addTestWar2EAR(EnterpriseArchive ear, Class testClass, String testWarName) throws IOException {
        //Vamos criar uma WAR com o ShrinkWrap

        /**
         * Em EAR's onde não existem WAR's basta adicionar a WAR ao EAR
         */

        /*
        final WebArchive war = ShrinkWrap.create(WebArchive.class, testWarName);
        war.addClass(testClass);
        ear.addAsModule(war);
        */
         //Neste caso o nosso EAR já tem uma WAR, posto isto de forma a que o Arquillian saiba qual o modulo a enriquecer para testar
         //É necessário determinar qual é o módulo de testes
        // Este topico refere isso mesmo: https://stackoverflow.com/questions/34590312/outofmemoryerror-in-arquillian
        //A forma de adicionar o módulo inclui um passo extra onde criamos um wrapper da WAR que define a mesma como sendo
        //a testavel
        final WebArchive war = ShrinkWrap.create(WebArchive.class, testWarName);
        war.addClass(testClass);
        WebArchive webArchive = Testable.archiveToTest(war);
        ear.addAsModule(webArchive);
        modifyApplicationXMLFaster(ear, testWarName);
    }
    // taken from http://stackoverflow.com/questions/14713129/how-to-add-test-classes-to-an-imported-ear-file-and-run-server-side-with-arquill/17036383#17036383
    private static void modifyApplicationXML(EnterpriseArchive ear, String testWarName) throws IOException {
        Node node = ear.get("META-INF/application.xml");


        DescriptorImporter<ApplicationDescriptor> importer = Descriptors.importAs(ApplicationDescriptor.class);
        try (InputStream is = node.getAsset().openStream()) {
            ApplicationDescriptor desc = importer.fromStream(is); // slow

            // append test.war to application.xml
            desc.webModule(testWarName, "/test");
            final String s = desc.exportAsString();
            Asset asset = new StringAsset(s);

            ear.delete(node.getPath());
            ear.setApplicationXML(asset);
        }
    }

    // ugly but faster than original one
    private static void modifyApplicationXMLFaster(EnterpriseArchive ear, String testWarName) throws IOException {
        Node node = ear.get("META-INF/application.xml");

        try (InputStream is = node.getAsset().openStream();
             InputStreamReader r = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(r)) {

            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                if (!"</application>".equals(line)) {
                    sb.append(line).append('\n');
                    continue;
                }

                sb.append(createModuleFragment(testWarName, "/test"));
            }

            final String modified = sb.toString();
            System.out.println(modified);
            ear.delete(node.getPath());
            ear.setApplicationXML(new StringAsset(modified));
        }
    }

    private static String createModuleFragment(String webUri, String contextRoot) {
        return "  <module>\n" +
                "    <web>\n" +
                "      <web-uri>" + webUri + "</web-uri>\n" +
                "      <context-root>" + contextRoot + "</context-root>\n" +
                "    </web>\n" +
                "  </module>\n" +
                "</application>";
    }

    @Inject
    usuarioService usuarioService;

    @Inject
    EntityManager em;

    @Inject
    Logger log;

    static Usuario a  = new Usuario();


    @Test
    public void testRegister() throws Exception {

        a.setNome("Joao");
        a.setNumero(123);
        a.setEmail("joao@ipportalegre.pt");
        usuarioService.salvar(a);
        assertNotNull(a.getId());
        log.info(a.getNome() + " was persisted with id " + a.getId());
        List<Usuario> all = usuarioService.findAll();
        assertTrue(all.size() > 1);
    }




    @Test
    public void testWEB1() {
        setBaseUrl("http://localhost:8080/kitchensink-ear-web");
        beginAt("testeAoStruts2.do");
        assertTitleEquals("Teste do struts");
        assertTextPresent("TESTE AOS LAYOUTS DO STRUTS 2");


        //testeAoFormStruts.do
        //clickLink("login");
        //assertTitleEquals("Login");
        //setTextField("username", "test");
        //setTextField("password", "test123");
        //submit();
        //assertTitleEquals("Welcome, test!");
    }

    @Test
    public void testWEB2()  {
        log.info("TEST WEB 2");
        setBaseUrl("http://localhost:8080/kitchensink-ear-web");

        beginAt("testeAoFormStruts.do");
        assertTitleEquals("Teste do struts");

        setWorkingForm("form2");

        setTextField("aluno.nome", "Nome 1");
        setTextField("aluno.numero", "0");

        submit();
        assertTextPresent("O numero tem de ser maior que ZERO");

        setWorkingForm("form2");

        setTextField("aluno.nome", "");
        setTextField("aluno.numero", "123");
        submit();
        assertTextPresent(" nome não pode ser vazio");


        setWorkingForm("form2");
        setTextField("aluno.nome", "Jonh Doe");
        setTextField("aluno.numero", "123");
        submit();
        assertTextPresent("NOME: Alterei o nome ok");




        //testeAoFormStruts.do
        //clickLink("login");
        //assertTitleEquals("Login");
        //setTextField("username", "test");
        //setTextField("password", "test123");
        //submit();
        //assertTitleEquals("Welcome, test!");
    }


    @Test
    public void testSaveService()  {
        log.info("testSaveService ");
        setBaseUrl("http://localhost:8080/kitchensink-ear-web");

        beginAt("testeAoFormStruts.do");
        assertTitleEquals("Teste do struts");

        setWorkingForm("form1");

        setTextField("aluno.nome", "Jonh Doe");
        setTextField("aluno.numero", "123");

        submit();


        IElement alunoId = getElementById("alunoId");
        String id = alunoId.getTextContent().trim();

        assertTrue(id != null && id.length() > 0);

        Usuario usuario = usuarioService.loadById(Long.parseLong(id));
        assertEquals(usuario.getNome(),"Jonh Doe");


        //testeAoFormStruts.do
        //clickLink("login");
        //assertTitleEquals("Login");
        //setTextField("username", "test");
        //setTextField("password", "test123");
        //submit();
        //assertTitleEquals("Welcome, test!");
    }



}
