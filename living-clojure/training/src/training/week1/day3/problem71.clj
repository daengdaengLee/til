(ns training.week1.day3.problem71)

(defn solve []
  (= (last (sort (rest (reverse [2 5 4 1 3 6]))))
     (-> [2 5 4 1 3 6] reverse rest sort last)
     5))
