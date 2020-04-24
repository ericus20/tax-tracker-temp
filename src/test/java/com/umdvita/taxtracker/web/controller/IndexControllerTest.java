package com.umdvita.taxtracker.web.controller;

import com.umdvita.taxtracker.WebTestConfig;
import com.umdvita.taxtracker.constant.ControllerConstant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class IndexControllerTest {

  private static MockMvc mockMvc;

  @BeforeAll
  static void beforeAll() {
    mockMvc = MockMvcBuilders.standaloneSetup(new IndexController())
            .setViewResolvers(WebTestConfig.viewResolver()).build();
  }

  @Test
  void root() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(ControllerConstant.INDEX_URL_MAPPING))
            .andExpect(MockMvcResultMatchers.view().name(ControllerConstant.INDEX_VIEW_NAME))
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void features() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(ControllerConstant.FEATURES_URL_MAPPING))
            .andExpect(MockMvcResultMatchers.view().name(ControllerConstant.FEATURES_VIEW_NAME))
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void whatToBring() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(ControllerConstant.WHAT_TO_BRING_URL_MAPPING))
            .andExpect(MockMvcResultMatchers.view().name(ControllerConstant.WHAT_TO_BRING_VIEW_NAME))
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void moreOnTaxes() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(ControllerConstant.MORE_ON_TAXES_URL_MAPPING))
            .andExpect(MockMvcResultMatchers.view().name(ControllerConstant.MORE_ON_TAXES_VIEW_NAME))
            .andExpect(MockMvcResultMatchers.status().isOk());
  }
}