package com.si.udriveservice.controller;

import com.si.udriveservice.UdriveServiceApplication;
import com.si.udriveservice.model.dto.DriverDTO;
import com.si.udriveservice.service.DriverService;
import com.si.udriveservice.service.mock.DriverMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class DriverIntTest extends AbstractIntTest {
    private static final String BASE_URL = "/driver";

    @Autowired
    private DriverService service;

//    @Autowired
//    private DriverMock mock;

    @Override
    protected DriverController getResourceIntTest() {
        return new DriverController(service);
    }

//    @Test
//    void saveSuccess() throws Exception {
//        DriverDTO dto = mock.mockDto();
//        DriverDTO returnEntity = jackson().getObjectMapper().readValue(
//                getMockMvc().perform(get(buildUrl(BASE_URL))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jackson().getObjectMapper().writeValueAsBytes(dto)))
//                        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), DriverDTO.class);
//        Assertions.assertTrue(returnEntity.getId() != null);
//
//    }
}
