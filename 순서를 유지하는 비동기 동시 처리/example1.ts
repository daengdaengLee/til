import { EventEmitter } from "node:events";

type Task<T> = { done: false } | { done: true; value: T };
type TaskWrapper<T> = { task: Task<T> };

export function example1<TInput, TOutput>(params: {
  source: EventEmitter;
  f: (value: TInput) => Promise<TOutput>;
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
  }
  function tick() {
    let taskWrapper = queue.at(0);
    while (taskWrapper != null && taskWrapper.task.done) {
      destination.emit(`data`, taskWrapper.task.value);
      queue.shift();
      taskWrapper = queue.at(0);
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

  return destination;
}
