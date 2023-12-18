(ns training.week1.day5.problem45)

(defn solve []
  (= [1 4 7 10 13] (take 5 (iterate #(+ 3 %) 1))))
