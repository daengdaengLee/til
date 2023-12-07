(ns training.week1.day2.problem14)

(defn solve1 []
  (= 8 ((fn add-five [x] (+ x 5)) 3)))

(defn solve2 []
  (= 8 ((fn [x] (+ x 5)) 3)))

(defn solve3 []
  (= 8 (#(+ % 5) 3)))

(defn solve4 []
  (= 8 ((partial + 5) 3)))
