package com.epam.jmp.maven.e2e;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestWebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class ClientE2ETest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void getAccount() throws Exception {
        String expectedResult = "[{'id':1,'firstName':'Bob','lastName':'Purson'}," +
                "{'id':2,'firstName':'2','lastName':'2'}," +
                "{'id':3,'firstName':'3','lastName':'3'}," +
                "{'id':4,'firstName':'4','lastName':'4'}," +
                "{'id':5,'firstName':'5','lastName':'5'}]";
        expectedResult = expectedResult.replaceAll("'", "\"");

        MvcResult result = this.mockMvc.perform(get("/api/v1/clients").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();

        String resultBody = result.getResponse().getContentAsString();
        assertEquals(expectedResult, resultBody);
    }

//    @Test(timeout = 2L)
//    public void test() throws Exception {
//        Thread.sleep(4L);
//    }
}
