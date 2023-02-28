package com.si.udriveservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class TestIntTest extends AbstractIntTest {
    private static final String BASE_URL = "/test";

    @Override
    protected TestController getResourceIntTest() {
        return new TestController();
    }

    @Test
    void testTwoIntegersSum() throws Exception {
        getMockMvc().perform(get(buildUrl(BASE_URL))).andDo(print())
                .andExpect(result -> Assertions.assertEquals("3", result.getResponse().getContentAsString()));
    }
}
