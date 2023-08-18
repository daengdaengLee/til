import { EventEmitter } from "node:events";

type Task<T> = { done: false } | { done: true; value: T };
type TaskWrapper<T> = { task: Task<T> };

export function run<TInput, TOutput>(params: {
  source: EventEmitter;
  f: (value: TInput) => Promise<TOutput>;
  concurrency: number;
}): EventEmitter {
  const destination = new EventEmitter();

  let isClosed = false;
  const queue: Array<TaskWrapper<TOutput>> = [];
  function handler(value: TInput): void {
    const taskWrapper: TaskWrapper<TOutput> = { task: { done: false } };
    queue.push(taskWrapper);
    params.f(value).then((value) => {
      taskWrapper.task = { done: true, value: value };
      tick();
    });

    if (queue.length < params.concurrency) {
      process.nextTick(() => {
        params.source.emit(`next`);
      });
    }
  }
  function tick() {
    let taskWrapper = queue.at(0);
    while (taskWrapper != null && taskWrapper.task.done) {
      destination.emit(`data`, taskWrapper.task.value);
      queue.shift();
      taskWrapper = queue.at(0);
    }

    if (queue.length < params.concurrency) {
      params.source.emit(`next`);
    }

    if (queue.length === 0 && isClosed) {
      params.source.off(`data`, handler);
      destination.emit(`close`);
    }
  }
  params.source.on(`data`, handler);
  params.source.once(`close`, () => {
    isClosed = true;
    tick();
  });

  process.nextTick(() => {
    params.source.emit(`next`);
  });

  return destination;
}
