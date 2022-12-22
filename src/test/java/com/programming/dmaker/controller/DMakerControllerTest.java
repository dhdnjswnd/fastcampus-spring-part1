package com.programming.dmaker.controller;

import com.programming.dmaker.dto.DeveloperDto;
import com.programming.dmaker.service.DMakerService;
import com.programming.dmaker.type.DeveloperLevel;
import com.programming.dmaker.type.DeveloperSkillType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DMakerController.class)
class DMakerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DMakerService dMakerService;

    protected MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);

    @Test
    public void getAllDevelopers() throws Exception {
        DeveloperDto juniorDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .memberId("memberId").build();
        DeveloperDto seniorDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.SENIOR)
                .developerSkillType(DeveloperSkillType.FULL_STACK)
                .memberId("memberId").build();
        given(dMakerService.getAllEmployedDevelopers()).willReturn(Arrays.asList(juniorDeveloperDto, seniorDeveloperDto));

        mockMvc.perform(get("/developers").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].developerSkillType",
                        CoreMatchers.is(DeveloperSkillType.FRONT_END.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].developerLevel",
                        CoreMatchers.is(DeveloperLevel.JUNIOR.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].developerSkillType",
                        CoreMatchers.is(DeveloperSkillType.FULL_STACK.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].developerLevel",
                        CoreMatchers.is(DeveloperLevel.SENIOR.name())));
    }

}