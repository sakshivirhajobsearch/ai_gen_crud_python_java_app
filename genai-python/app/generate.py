from transformers import pipeline

generator = pipeline("text-generation", model="gpt2")

def generate_text(prompt: str) -> str:
    output = generator(prompt, max_length=50, num_return_sequences=1)
    return output[0]["generated_text"]
