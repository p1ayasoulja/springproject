package com.example.sweater;

import com.example.sweater.controller.MessageController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "qwert")
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MessageController controller;
    @Test
    public void messageListTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(MockMvcResultMatchers.xpath("//div[@id='message-list']/div").nodeCount(4));
    }

//    @Test
//    public void filterMessageTest() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/main").param("filter", "why"))
//                .andDo(print())
//                .andExpect(authenticated())
//                .andExpect(MockMvcResultMatchers.xpath("//div[@id='message-list']/div").nodeCount(4))
//                .andExpect(MockMvcResultMatchers.xpath("//div[@id='message-list']/div[@data-id=1]").exists())
//                .andExpect(MockMvcResultMatchers.xpath("//div[@id='message-list']/div[@data-id=4]").exists());
//    }

    @Test
    public void addMessageToListTest() throws Exception {
        MockHttpServletRequestBuilder multipart = MockMvcRequestBuilders.multipart("/main")
                .file("file", "123".getBytes())
                .param("text", "fifth")
                .param("tag", "new one")
                .with(SecurityMockMvcRequestPostProcessors.csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(MockMvcResultMatchers.xpath("//div[@id='message-list']/div").nodeCount(5))
                .andExpect(MockMvcResultMatchers.xpath("//div[@id='message-list']/div[@data-id=10]").exists())
                .andExpect(MockMvcResultMatchers.xpath("//div[@id='message-list']/div[@data-id=10]/div/span").string("fifth"))
                .andExpect(MockMvcResultMatchers.xpath("//div[@id='message-list']/div[@data-id=10]/div/i").string("#new one"));


    }
}

