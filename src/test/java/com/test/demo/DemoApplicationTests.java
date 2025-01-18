package com.test.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void testMaskValues() {
		// Input and output JSON file paths
		String inputFilePath = "src\\test\\java\\com\\test\\resources\\source.json";
		String outputFilePath = "src\\test\\java\\com\\test\\resources\\output.json";

		try {
			// Create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			// Read the JSON file into a JsonNode
			JsonNode rootNode = objectMapper.readTree(new File(inputFilePath));

			// Mask the sensitive fields
			maskSensitiveData(rootNode);

			// Write the modified JSON back to a file
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFilePath), rootNode);

			System.out.println("Masked JSON saved to: " + outputFilePath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void maskSensitiveData(JsonNode node) {
		if (node.isObject()) {
			ObjectNode objectNode = (ObjectNode) node;

			// Example: Mask specific fields
			if (objectNode.has("company")) {
				objectNode.put("company", "****");
			}
			if (objectNode.has("phone")) {
				objectNode.put("phone", "****");
			}

			// just add the fields you want to mask in the if loop


			// Recursively process child nodes
			node.fields().forEachRemaining(entry -> maskSensitiveData(entry.getValue()));
		} else if (node.isArray()) {
			// Iterate over elements in an array
			Object JsonMasking = new Object();
			node.forEach(DemoApplicationTests::maskSensitiveData);
		}
	}
}

