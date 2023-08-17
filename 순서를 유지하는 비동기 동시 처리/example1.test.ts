import { EventEmitter } from "node:events";
import { delay } from "./delay";
import { example1 } from "./example1";

const data = [1, 2, 3, 4, 5];
const expected = [`1`, `2`, `3`, `4`, `5`];
const delayMs = 1000;
const f = async (value: number): Promise<string> => {
  await delay(delayMs);
  return `${value}`;
};

describe(`Example 1`, () => {
  it(`Keep the original sequence.`, async () => {
    const source = new EventEmitter();
    const destination = example1({ source: source, f: f });
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

    for (const value of data) {
      source.emit(`data`, value);
    }
    source.emit(`close`);
    await done;

    expect(output).toEqual(expected);
  });

  it(`Run concurrently`, async () => {
    const source = new EventEmitter();
    const destination = example1({ source: source, f: f });
    const done = new Promise<void>((resolve) => {
      destination.once(`close`, () => {
        resolve();
      });
    });

    const start = Date.now();
    for (const value of data) {
      source.emit(`data`, value);
    }
    source.emit(`close`);
    await done;
    const end = Date.now();
    const duration = end - start;

    const toleration = 30;
    expect(duration).toBeGreaterThanOrEqual(delayMs - toleration);
    expect(duration).toBeLessThanOrEqual(delayMs + toleration);
  });
});
