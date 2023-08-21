package com.api.product;

import com.api.product.model.Product;
import com.api.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductWebServiceApplicationTests {

	@Autowired
	private ProductService pd;

	@Test
	void testeAddProduct() {
		String json = "[\n" +
				"    {\n" +
				"        \"id\": 70,\n" +
				"        \"name\": \"test1\",\n" +
				"        \"brand\": \"ccccc's\",\n" +
				"        \"price\": \"2.5\"\n" +
				"    }]";

		pd.insert(json);

		// Search
		List<Product> productList = pd.select(70L);

		// Ensure that the returned list is not empty
		assertNotNull(productList);
		// Ensure that the list has exactly one product
		assertEquals(1, productList.size());

		//Tests
		Product product = productList.get(0);
		assertEquals(70L, product.getId());
		assertEquals("test1", product.getName());
		assertEquals("ccccc's", product.getBrand());
		assertEquals(2.5, product.getPrice());
	}
}
