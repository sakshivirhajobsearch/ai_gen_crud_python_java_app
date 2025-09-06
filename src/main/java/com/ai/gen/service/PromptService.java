package com.ai.gen.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ai.gen.model.Prompt;

public class PromptService {
	private Map<Integer, Prompt> prompts = new HashMap<>();
	private int idCounter = 1;

	public List<Prompt> getAll() {
		return new ArrayList<>(prompts.values());
	}

	public Prompt getById(int id) {
		return prompts.get(id);
	}

	public Prompt create(Prompt prompt) {
		prompt.setId(idCounter++);
		prompts.put(prompt.getId(), prompt);
		return prompt;
	}

	public Prompt update(int id, Prompt updatedPrompt) {
		updatedPrompt.setId(id);
		prompts.put(id, updatedPrompt);
		return updatedPrompt;
	}

	public void delete(int id) {
		prompts.remove(id);
	}
}
