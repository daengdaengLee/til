from dotenv import dotenv_values
from fastapi import FastAPI
from fastapi.responses import StreamingResponse
from fastapi.staticfiles import StaticFiles
from openai import AsyncOpenAI
from pydantic import BaseModel

config = dotenv_values(".env")
OPENAI_API_KEY = config["OPENAI_API_KEY"]

client = AsyncOpenAI(api_key=OPENAI_API_KEY)

app = FastAPI()
app.mount("/static", StaticFiles(directory="static"), name="static")


async def test_streamer(n: int):
    stream = await client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=[{"role": "user", "content": f"아무 말이나 {n}문장 써 줘."}],
        stream=True,
    )
    async for chunk in stream:
        if chunk.choices[0].delta.content is not None:
            yield f"data: {chunk.choices[0].delta.content}\n\n"


class PostSseTestReqBody(BaseModel):
    n: int


@app.post("/sse-test")
def post_sse_test_controller(body: PostSseTestReqBody):
    return StreamingResponse(test_streamer(body.n), media_type="text/event-stream")
