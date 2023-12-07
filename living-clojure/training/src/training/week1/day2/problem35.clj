(ns training.week1.day2.problem35)

(defn solve1 []
  (= 7 (let [x 5] (+ 2 x))))

(defn solve2 []
  (= 7 (let [x 3, y 10] (- y x))))

(defn solve3 []
  (= 7 (let [x 21] (let [y 3] (/ x y)))))
