(ns training.week1.day4.problem25)

(defn solve1 []
  (= (#(filter odd? %) #{1 2 3 4 5}) '(1 3 5)))

(defn solve2 []
  (= (#(filter odd? %) [4 2 1 6]) '(1)))

(defn solve3 []
  (= (#(filter odd? %) [2 2 4 6]) '()))

(defn solve4 []
  (= (#(filter odd? %) [1 1 1 3]) '(1 1 1 3)))