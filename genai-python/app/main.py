from flask import Flask, request, jsonify
import uuid

app = Flask(__name__)

# In-memory store to simulate a database
database = {}

# Health check or root endpoint
@app.route('/', methods=['GET'])
def home():
    return '✅ Gen AI CRUD App is Running!', 200

# Generate output from a prompt (dummy implementation)
@app.route('/generate', methods=['POST'])
def generate():
    data = request.get_json()
    prompt = data.get('prompt')

    if not prompt:
        return jsonify({'error': 'Missing prompt'}), 400

    # Simulate GenAI generation
    generated_output = f"Generated result for: {prompt}"

    # Save to database with UUID
    data_id = str(uuid.uuid4())
    database[data_id] = {
        'id': data_id,
        'prompt': prompt,
        'result': generated_output
    }

    return jsonify(database[data_id]), 201

# Save manually provided data
@app.route('/data', methods=['POST'])
def save_data():
    data = request.get_json()
    prompt = data.get('prompt')
    result = data.get('result')

    if not prompt or not result:
        return jsonify({'error': 'Missing prompt or result'}), 400

    data_id = str(uuid.uuid4())
    database[data_id] = {
        'id': data_id,
        'prompt': prompt,
        'result': result
    }

    return jsonify(database[data_id]), 201

# Read data by ID
@app.route('/data/<data_id>', methods=['GET'])
def get_data(data_id):
    if data_id in database:
        return jsonify(database[data_id]), 200
    return jsonify({'error': 'Data not found'}), 404

# Update data by ID
@app.route('/data/<data_id>', methods=['PUT'])
def update_data(data_id):
    if data_id not in database:
        return jsonify({'error': 'Data not found'}), 404

    data = request.get_json()
    prompt = data.get('prompt')
    result = data.get('result')

    if prompt:
        database[data_id]['prompt'] = prompt
    if result:
        database[data_id]['result'] = result

    return jsonify(database[data_id]), 200

# Delete data by ID
@app.route('/data/<data_id>', methods=['DELETE'])
def delete_data(data_id):
    if data_id in database:
        deleted = database.pop(data_id)
        return jsonify({'deleted': deleted}), 200
    return jsonify({'error': 'Data not found'}), 404

if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5001, debug=True)
