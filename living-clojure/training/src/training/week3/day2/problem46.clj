(ns training.week3.day2.problem46)

(defn reverse-params [f]
  (fn [& args] (apply f (reverse args))))

(defn solve1 []
  (= 3 ((reverse-params nth) 2 [1 2 3 4 5])))

(defn solve2 []
  (= true ((reverse-params >) 7 8)))

(defn solve3 []
  (= 4 ((reverse-params quot) 2 8)))

(defn solve4 []
  (= [1 2 3] ((reverse-params take) [1 2 3 4 5] 3)))