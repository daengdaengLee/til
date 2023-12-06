(ns training.week1.day1.problem5)

(defn solve1 []
  (= '(1 2 3 4) (conj '(2 3 4) 1)))

(defn solve2 []
  (= '(1 2 3 4) (conj '(3 4) 2 1)))
