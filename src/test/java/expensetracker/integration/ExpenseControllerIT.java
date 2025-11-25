package expensetracker.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ExpenseControllerIT {
	
	@Autowired
	private MockMvc mockMvc; 
	
	@Autowired
	private ObjectMapper mapper; 

	@Test
	void createExpense_success() throws Exception {
		String jsonRequest = """
			{
				"description": "comprato droga buona",
				"amount" : 174.20,
				"category": "food",
				"type": "expense",
				"ownerId": 1
			}
				""";
		
		MvcResult result = mockMvc
				.perform(post("/api/expenses/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andReturn();
		
		int status = result.getResponse().getStatus();
		assertEquals(status, 201);
		
		String body = result.getResponse().getContentAsString();
		JsonNode node = mapper.readTree(body); 
		
		assertNotNull(node.get("id"));
	}
	
	@Test
	void createExpense_categoryError() throws Exception {
		String jsonRequest = """
				{
					"description": "comprato droga buona",
					"amount" : 174.20,
					"category": "merda",
					"type": "expense",
					"ownerId": 1
				}
					""";
			
			MvcResult result = mockMvc
					.perform(post("/api/expenses/")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonRequest))
					.andReturn();
			
			int status = result.getResponse().getStatus();
			assertEquals(400, status);
			
			String body = result.getResponse().getContentAsString();
			JsonNode node = mapper.readTree(body); 
			
			assertNotNull(node.get("message"));
	}
	
	@Test
	void createExpense_typeError() throws Exception {
		String jsonRequest = """
				{
					"description": "comprato droga buona",
					"amount" : 174.20,
					"category": "food",
					"type": "cinese",
					"ownerId": 1
				}
					""";
			
			MvcResult result = mockMvc
					.perform(post("/api/expenses/")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonRequest))
					.andReturn();
			
			int status = result.getResponse().getStatus();
			assertEquals(400, status);
			
			String body = result.getResponse().getContentAsString();
			JsonNode node = mapper.readTree(body); 
			
			assertNotNull(node.get("message"));
	}
	
	@Test
	void createExpense_ownerNotFound() throws Exception {
		String jsonRequest = """
				{
					"description": "comprato droga buona",
					"amount" : 174.20,
					"category": "food",
					"type": "expense",
					"ownerId": 100
				}
					""";
			
			MvcResult result = mockMvc
					.perform(post("/api/expenses/")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonRequest))
					.andReturn();
			
			int status = result.getResponse().getStatus();
			assertEquals(400, status);
			
			String body = result.getResponse().getContentAsString();
			JsonNode node = mapper.readTree(body); 
			
			assertNotNull(node.get("message"));
	}
}
