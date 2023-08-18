import { EventEmitter } from "node:events";
import { delay } from "./delay";
import { run } from "./example2";

const data = [1, 2, 3, 4, 5];
const expected = [`1`, `2`, `3`, `4`, `5`];
const concurrency = 2;
const delayMs = 1000;
const expectedMs = 3000;
const f = async (value: number): Promise<string> => {
  await delay(delayMs);
  return `${value}`;
};

describe(`Example 1`, () => {
  it(`Keep the original sequence.`, async () => {
    const source = new EventEmitter();
    const destination = run({ source: source, f: f, concurrency: concurrency });
    const output: Array<string> = [];
    const handler = (value: string): void => {
      output.push(value);
    };
    destination.on(`data`, handler);
    const done = new Promise<void>((resolve) => {
      destination.once(`close`, () => {
        destination.off(`data`, handler);
        resolve();
      });
    });

    let i = 0;
    source.on(`next`, function handleNext() {
      const value = data.at(i);
      if (value == null) {
        source.off(`next`, handleNext);
        source.emit(`close`);
        return;
      }

      source.emit(`data`, value);
      i += 1;
    });
    await done;

    expect(output).toEqual(expected);
  });

  it(`Run concurrently.`, async () => {
    const source = new EventEmitter();
    const destination = run({ source: source, f: f, concurrency: concurrency });
    const done = new Promise<void>((resolve) => {
      destination.once(`close`, () => {
        resolve();
      });
    });

    const start = Date.now();
    let i = 0;
    source.on(`next`, function handleNext() {
      const value = data.at(i);
      if (value == null) {
        source.off(`next`, handleNext);
        source.emit(`close`);
        return;
      }

      source.emit(`data`, value);
      i += 1;
    });
    await done;
    const end = Date.now();
    const duration = end - start;

    const toleration = 30;
    expect(duration).toBeGreaterThanOrEqual(expectedMs - toleration);
    expect(duration).toBeLessThanOrEqual(expectedMs + toleration);
  });
});
