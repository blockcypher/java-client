package com.blockcypher.service;

import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.info.Info;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertFalse;


/**
 * Test for InfoService
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public class InfoServiceTest extends AbstractServiceTest {

    private static final Logger logger = Logger.getLogger(InfoServiceTest.class);

    @Test
    public void testGetInfo() throws BlockCypherException, IOException {
        Info info = blockCypherContext.getInfoService().getInfo();
        assertFalse(StringUtils.isBlank(info.getName()));
    }

}
