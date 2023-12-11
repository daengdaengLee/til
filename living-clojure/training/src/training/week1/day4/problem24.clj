(ns training.week1.day4.problem24)

(defn solve1 []
  (= (#(reduce + 0 %) [1 2 3]) 6))

(defn solve2 []
  (= (#(reduce + 0 %) (list 0 -2 5 5)) 8))

(defn solve3 []
  (= (#(reduce + 0 %) #{4 2 1}) 7))

(defn solve4 []
  (= (#(reduce + 0 %) '(0 0 -1)) -1))

(defn solve5 []
  (= (#(reduce + 0 %) '(1 10 3)) 14))
