package app.demo.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import app.demo.def.MessageConst;
import app.demo.def.MessageModel;
import app.demo.entity.DefCurrencyEntity;
import app.demo.util.JsonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testQueryCurrency() throws Exception {
		DefCurrencyEntity entity = new DefCurrencyEntity();
		entity.setCurrencyCode("USD");
		entity.setDescription("美元");
		String expected = JsonUtil.objToString(entity);
		mockMvc.perform(MockMvcRequestBuilders.get("/query?currencyCode=USD")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expected));
	}

	@Test
	public void testQueryNotExistCurrency() throws Exception {
		MessageModel model = new MessageModel("ABC" + MessageConst.IS_NOT_EXIST.getMessage());
		String expected = JsonUtil.objToString(model);
		mockMvc.perform(MockMvcRequestBuilders.get("/query?currencyCode=ABC"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expected));
	}

	@Test
	public void testAddCurrency() throws Exception {
		DefCurrencyEntity entity = new DefCurrencyEntity();
		entity.setCurrencyCode("TWD");
		entity.setDescription("新台幣");
		String postBody = JsonUtil.objToString(entity);

		MessageModel model = new MessageModel(MessageConst.SUCCESS);
		String expected = JsonUtil.objToString(model);

		mockMvc.perform(MockMvcRequestBuilders.post("/add").contentType(MediaType.APPLICATION_JSON).content(postBody))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expected));
	}

	@Test
	public void testAddDuplicateCurrency() throws Exception {
		DefCurrencyEntity entity = new DefCurrencyEntity();
		entity.setCurrencyCode("USD");
		entity.setDescription("美元");
		String postBody = JsonUtil.objToString(entity);

		MessageModel model = new MessageModel("USD" + MessageConst.ALREADY_EXIST.getMessage());
		String expected = JsonUtil.objToString(model);

		mockMvc.perform(MockMvcRequestBuilders.post("/add").contentType(MediaType.APPLICATION_JSON).content(postBody))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expected));
	}

	@Test
	public void testDeleteCurrency() throws Exception {
		MessageModel model = new MessageModel(MessageConst.SUCCESS);
		String expected = JsonUtil.objToString(model);
		mockMvc.perform(MockMvcRequestBuilders.delete("/delete?currencyCode=USD"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expected));
	}

	@Test
	public void testDeleteNotExistCurrency() throws Exception {
		MessageModel model = new MessageModel("JPY" + MessageConst.IS_NOT_EXIST.getMessage());
		String expected = JsonUtil.objToString(model);
		mockMvc.perform(MockMvcRequestBuilders.delete("/delete?currencyCode=JPY"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expected));
	}

	@Test
	public void testUpdateCurrency() throws Exception {
		DefCurrencyEntity entity = new DefCurrencyEntity();
		entity.setCurrencyCode("USD");
		entity.setDescription("美金");
		String postBody = JsonUtil.objToString(entity);

		mockMvc.perform(MockMvcRequestBuilders.put("/update").contentType(MediaType.APPLICATION_JSON).content(postBody))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(postBody));
	}

	@Test
	public void testUpdateNotExistCurrency() throws Exception {
		DefCurrencyEntity entity = new DefCurrencyEntity();
		entity.setCurrencyCode("JPY");
		entity.setDescription("日圓");
		String postBody = JsonUtil.objToString(entity);

		MessageModel model = new MessageModel("JPY" + MessageConst.IS_NOT_EXIST.getMessage());
		String expected = JsonUtil.objToString(model);
		mockMvc.perform(MockMvcRequestBuilders.put("/update").contentType(MediaType.APPLICATION_JSON).content(postBody))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expected));
	}

	@Test
	public void testGetCoinDesk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/coindesk")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetCoinDeskAndConvert() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/coindesk/convert")).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
