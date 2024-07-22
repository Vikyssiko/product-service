package com.example.test_task.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public abstract class AbstractControllerTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @SneakyThrows
    private ResultActions sendData(final HttpMethod method,
                                     final String url,
                                     final Object object) {
        final var request = MockMvcRequestBuilders.request(method, url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(object));
        return mvc.perform(request).andExpect(status().isCreated());
    }

    @SneakyThrows
    protected ResultActions sendInvalidData(final HttpMethod method,
                                     final String url,
                                     final Object object) {
        final var request = MockMvcRequestBuilders.request(method, url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(object));
        return mvc.perform(request).andExpect(status().isBadRequest());
    }

    @SneakyThrows
    protected <T> T sendData(final HttpMethod method,
                             final String url,
                             final Object object,
                             final Class<T> clazz) {
        final var actualString = sendData(method, url, object)
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(actualString, clazz);
    }

    @SneakyThrows
    protected <T> T getData(final String url,
                            final TypeReference<T> type) {
        final String actualString = mvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return objectMapper.readValue(actualString, type);
    }

    @SneakyThrows
    protected void testBadRequest(final HttpMethod method,
                                    final String url) {
        mvc.perform(MockMvcRequestBuilders.request(method, url))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    protected void testNoContent(final HttpMethod method,
                                  final String url) {
        mvc.perform(MockMvcRequestBuilders.request(method, url))
                .andExpect(status().isNoContent());
    }
}
