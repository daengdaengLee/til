(ns training.week2.day1.problem48)

(defn solve1 []
  (= 6 (some #{2 7 6} [5 6 7 8])))

(defn solve2 []
  (= 6 (some #(when (even? %) %) [5 6 7 8])))
