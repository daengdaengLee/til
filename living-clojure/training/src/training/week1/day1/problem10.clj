(ns training.week1.day1.problem10)

(defn solve1 []
  (= 20 ((hash-map :a 10, :b 20, :c 30) :b)))

(defn solve2 []
  (= 20 (:b {:a 10, :b 20, :c 30})))
