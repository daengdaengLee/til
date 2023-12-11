(ns training.week1.day4.problem27)

(defn solve1 []
  (false? (#(= (seq %) (reverse (seq %))) '(1 2 3 4 5))))

(defn solve2 []
  (true? (#(= (seq %) (reverse (seq %))) "racecar")))

(defn solve3 []
  (true? (#(= (seq %) (reverse (seq %))) [:foo :bar :foo])))

(defn solve4 []
  (true? (#(= (seq %) (reverse (seq %))) '(1 1 3 3 1 1))))

(defn solve5 []
  (false? (#(= (seq %) (reverse (seq %))) '(:a :b :c))))
