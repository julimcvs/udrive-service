package com.si.udriveservice.controller;

import com.si.udriveservice.configuration.GlobalAdviceController;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Field;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public abstract class AbstractIntTest {

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private GlobalAdviceController globalAdviceController;

    private MockMvc mockMvc;

    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    protected MappingJackson2HttpMessageConverter jackson() {
        return jacksonMessageConverter;
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(getResourceIntTest())
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter, new ResourceHttpMessageConverter())
                .setControllerAdvice(globalAdviceController)
                .build();
    }

    protected String buildUrl(String... params) {
        return String.join("/", params);
    }

    protected void saveWithNullField(String baseUrl, Object dto, String fieldName)  throws Exception {
        Field field = dto.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(dto, null);
        String className = dto.getClass().getSimpleName();
        String formattedClassName = className.replaceFirst(className.substring(0,1), className.substring(0,1).toLowerCase());
        this.getMockMvc().perform(post(buildUrl(baseUrl))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jackson().getObjectMapper().writeValueAsBytes(dto)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> Assertions.assertTrue(Objects.requireNonNull(result.getResolvedException()).getMessage().contains(String.format("Field error in object '%s' on field '%s': rejected value [null]", formattedClassName, fieldName))));
    }

    protected abstract Object getResourceIntTest();
}
