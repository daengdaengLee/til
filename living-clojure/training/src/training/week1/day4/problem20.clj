(ns training.week1.day4.problem20)

(defn solve1 []
  (= ((comp second reverse) (list 1 2 3 4 5)) 4))

(defn solve2 []
  (= ((comp second reverse) ["a" "b" "c"]) "b"))

(defn solve3 []
  (= ((comp second reverse) [[1 2] [3 4]]) [1 2]))
