import flask
from dotenv import dotenv_values
from flask import Flask, render_template
from openai import OpenAI

config = dotenv_values(".env")
OPENAI_API_KEY = config["OPENAI_API_KEY"]

client = OpenAI(api_key=OPENAI_API_KEY)

app = Flask(__name__)


@app.get("/static/index.html")
def get_index_controller():
    return render_template("index.html")


def test_streamer(n: int):
    stream = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=[{"role": "user", "content": f"아무 말이나 {n}문장 써 줘."}],
        stream=True,
    )
    for chunk in stream:
        if chunk.choices[0].delta.content is not None:
            yield f"data: {chunk.choices[0].delta.content}\n\n"


@app.post("/sse-test")
def post_sse_test():
    return flask.Response(test_streamer(10), mimetype="text/event-stream")


app.run(debug=True)
