package com.ai.gen.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ai.gen.model.Prompt;
import com.ai.gen.service.PromptService;

@RestController
@RequestMapping("/api/prompts")
public class PromptController {
	private final PromptService service = new PromptService();

	@GetMapping
	public List<Prompt> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Prompt get(@PathVariable int id) {
		return service.getById(id);
	}

	@PostMapping
	public Prompt create(@RequestBody Prompt prompt) {
		return service.create(prompt);
	}

	@PutMapping("/{id}")
	public Prompt update(@PathVariable int id, @RequestBody Prompt prompt) {
		return service.update(id, prompt);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		service.delete(id);
	}

	@PostMapping("/generate/{id}")
	public ResponseEntity<String> generate(@PathVariable int id) {
		Prompt prompt = service.getById(id);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:5001/generate";

		// ✅ Java 8 compatible - use new HashMap instead of Map.of
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("prompt", prompt.getPrompt());

		ResponseEntity<String> response = restTemplate.postForEntity(url, requestMap, String.class);
		return response;
	}
}