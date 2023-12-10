(ns training.week1.day3.problem37)

(defn solve []
  (= "ABC" (apply str (re-seq #"[A-Z]+" "bA1B3Ce "))))
