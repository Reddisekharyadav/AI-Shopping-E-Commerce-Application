from flask import Flask, render_template, request, jsonify
from flask_cors import CORS
import openai
from openai.types.chat import ChatCompletionMessageParam
import sqlite3
from functools import lru_cache

app = Flask(__name__)
CORS(app)

DATABASE_FILE = "database.db"

# Set your OpenAI API key from environment variable
import os
openai.api_key = os.getenv("OPENAI_API_KEY")  # Loaded securely from .env, do not commit secrets

# Initialize Database (SQLite) to store chat history
def init_db():
    conn = sqlite3.connect(DATABASE_FILE)
    cursor = conn.cursor()
    cursor.execute("""CREATE TABLE IF NOT EXISTS chat_history (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_message TEXT,
                        bot_response TEXT)""")
    conn.commit()
    conn.close()

init_db()  # Initialize the database

@lru_cache(maxsize=50)
def get_chat_response(user_input, chat_history_tuple):
    try:
        chat_history: list[ChatCompletionMessageParam] = [
            {"role": key, "content": value} for key, value in chat_history_tuple
        ]
        client = openai.OpenAI(api_key=openai.api_key)
        response = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=chat_history + [{"role": "user", "content": user_input}]
        )
        return response.choices[0].message.content
    except Exception as e:
        return f"Error: {str(e)}"
        return f"Error: {str(e)}"

@app.route("/")
def home():
    return render_template("index.html")

@app.route("/ask", methods=["POST"])
def ask():
    if not request.is_json or request.json is None:
        return jsonify({"error": "Invalid or missing JSON in request"}), 400
    user_input = request.json.get("message")
    if not user_input:
        return jsonify({"error": "No user input provided"}), 400
    # Retrieve previous chat history from the database
    conn = sqlite3.connect(DATABASE_FILE)
    conn.row_factory = sqlite3.Row
    cursor = conn.cursor()
    cursor.execute("SELECT user_message, bot_response FROM chat_history ORDER BY id DESC LIMIT 5")
    past_chats = cursor.fetchall()
    conn.close()

    # Convert chat history into OpenAI format
    chat_history = []
    for row in past_chats:
        chat_history.append({"role": "user", "content": row["user_message"]})
        chat_history.append({"role": "assistant", "content": row["bot_response"]})

    chat_history_tuple = tuple((d["role"], d["content"]) for d in chat_history)

    # Get response from OpenAI
    response = get_chat_response(user_input, chat_history_tuple)

    # Save chat to database
    conn = sqlite3.connect(DATABASE_FILE)
    cursor = conn.cursor()
    cursor.execute("INSERT INTO chat_history (user_message, bot_response) VALUES (?, ?)", (user_input, response))
    conn.commit()
    conn.close()

    return jsonify({"response": response})

if __name__ == "__main__":
    app.run(debug=True)
