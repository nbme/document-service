package org.nbme.wbti.document;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nbme.wbti.document.configuration.PersistenceConfig;
import org.nbme.wbti.document.model.DocumentAction;
import org.nbme.wbti.document.model.DocumentProcess;
import org.nbme.wbti.document.service.DocumentProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by rwang on 11/24/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "classpath:/app-config.xml"
//@ContextConfiguration("/")
@ContextConfiguration(classes = PersistenceConfig.class)
//@ComponentScan("org.nbme.wbti.document.service.impl")
//@ActiveProfiles("dev")
public class DocumentProcessHibernateTest {
    @Autowired
    DocumentProcessService documentProcessService;
    @Before
    public void setup(){
        addDocumentProcess();

    }

    private int addDocumentProcess() {
        DocumentProcess documentProcess =  new DocumentProcess();
        documentProcess.setAction(DocumentAction.CREATE);
        documentProcess.setSourceUrl("file///r:\\rwang\\test\\nbme.jpg");
        documentProcess.setTargetUrl("ftp://nbme.org/rwang/test/nbme.jpg");

        return documentProcessService.addDocumentProcess(documentProcess);
    }

    @Test
      public void testGetObjects()
    {
        List results =  documentProcessService.getDocumentProcesses();
        assertTrue(results.size() > 0);
    }
    @Test
    public void testGetObject()
    {
        DocumentProcess result = documentProcessService.getDocumentProcess(0);
        assertNotNull(result);
    }
    @Test
    public void testAddAndDeleteObject()
    {
        DocumentProcess dp = new DocumentProcess();
        dp.setAction(DocumentAction.UPDATE);
        dp.setSourceUrl("test");
        dp.setTargetUrl("test2");
        int id = documentProcessService.addDocumentProcess(dp);
        DocumentProcess dp2 = documentProcessService.getDocumentProcess(id);
        assertEquals(dp.getAction(), dp2.getAction());

        documentProcessService.removeDocumentProcess(id);
        assertNotNull( documentProcessService.getDocumentProcess(id));
    }
}
