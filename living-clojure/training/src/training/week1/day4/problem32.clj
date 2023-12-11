(ns training.week1.day4.problem32)

(defn solve1 []
  (= (#(reduce (fn [acc cur] (concat acc (list cur cur))) '() %) [1 2 3]) '(1 1 2 2 3 3)))

(defn solve2 []
  (= (#(reduce (fn [acc cur] (concat acc (list cur cur))) '() %) [:a :a :b :b]) '(:a :a :a :a :b :b :b :b)))

(defn solve3 []
  (= (#(reduce (fn [acc cur] (concat acc (list cur cur))) '() %) [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4])))

(defn solve4 []
  (= (#(reduce (fn [acc cur] (concat acc (list cur cur))) '() %) [44 33]) [44 44 33 33]))
