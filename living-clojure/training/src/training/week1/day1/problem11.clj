(ns training.week1.day1.problem11)

(defn solve []
  (= {:a 1, :b 2, :c 3} (conj {:a 1} {:b 2} [:c 3])))
