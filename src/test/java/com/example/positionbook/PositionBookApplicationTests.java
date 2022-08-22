package com.example.positionbook;

import com.example.positionbook.controllers.PositionBookController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
class PositionBookApplicationTests {

	@Autowired
	private PositionBookController positionBookController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(positionBookController).build();
	}

	@Test
	void testBuyingSecurities() throws Exception {
		String newEventsJson = "{\"Events\":[{\"ID\":1,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":100},{\"ID\":2,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50}]}";
		mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).content(newEventsJson));

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/positions")).andReturn();
		String content = result.getResponse().getContentAsString();

		String expected = "{\"Positions\":[{\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":150,\"Events\":[{\"ID\":1,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":100},{\"ID\":2,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50}]}]}";
		assertEquals(expected,content);
	}

	@Test
	void testBuyingDifferentSecurities() throws Exception {
		String newEventsJson = "{\\\"Events\\\":[{\\\"ID\\\":3,\\\"Action\\\":\\\"BUY\\\",\\\"Account\\\":\\\"ACC1\\\",\\\"Security\\\":\\\"SEC1\\\",\\\"Quantity\\\":12},{\\\"ID\\\":4,\\\"Action\\\":\\\"BUY\\\",\\\"Account\\\":\\\"ACC1\\\",\\\"Security\\\":\\\"SECXYZ\\\",\\\"Quantity\\\":50},{\\\"ID\\\":5,\\\"Action\\\":\\\"BUY\\\",\\\"Account\\\":\\\"ACC2\\\",\\\"Security\\\":\\\"SECXYZ\\\",\\\"Quantity\\\":33},{\\\"ID\\\":6,\\\"Action\\\":\\\"BUY\\\",\\\"Account\\\":\\\"ACC1\\\",\\\"Security\\\":\\\"SEC1\\\",\\\"Quantity\\\":20}]}";
		mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).content(newEventsJson));

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/positions")).andReturn();
		String content = result.getResponse().getContentAsString();

		String expected = "{\"Positions\":[{\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":150,\"Events\":[{\"ID\":1,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":100},{\"ID\":2,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50}]},{\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50,\"Events\":[{\"ID\":7,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":100},{\"ID\":8,\"Action\":\"SELL\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50}]}]}";
		assertEquals(expected,content);
	}

	@Test
	void testBuyingAndSellingSecurities() throws Exception {
		String newEventsJson = "{\"Events\":[{\"ID\":7,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":100},{\"ID\":8,\"Action\":\"SELL\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50}]}";
		mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).content(newEventsJson));

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/positions")).andReturn();
		String content = result.getResponse().getContentAsString();

		String expected = "{\"Positions\":[{\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":150,\"Events\":[{\"ID\":1,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":100},{\"ID\":2,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50}]},{\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50,\"Events\":[{\"ID\":7,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":100},{\"ID\":8,\"Action\":\"SELL\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50}]}]}";
		assertEquals(expected,content);
	}

	@Test
	void testCancellingEvents() throws Exception {
		String newEventsJson = "{\"Events\":[{\"ID\":9,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":100},{\"ID\":9,\"Action\":\"CANCEL\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":0},{\"ID\":10,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":5}]}";
		mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON).content(newEventsJson));

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/positions")).andReturn();
		String content = result.getResponse().getContentAsString();

		String expected = "{\"Positions\":[{\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":150,\"Events\":[{\"ID\":1,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":100},{\"ID\":2,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50}]},{\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50,\"Events\":[{\"ID\":7,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":100},{\"ID\":8,\"Action\":\"SELL\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":50}]},{\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":5,\"Events\":[{\"ID\":9,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":100},{\"ID\":9,\"Action\":\"CANCEL\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":0},{\"ID\":10,\"Action\":\"BUY\",\"Account\":\"ACC1\",\"Security\":\"SEC1\",\"Quantity\":5}]}]}";
		assertEquals(expected,content);
	}

}
