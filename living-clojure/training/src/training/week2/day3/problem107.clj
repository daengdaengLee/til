(ns training.week2.day3.problem107)

(defn solve1 []
  (= 256 (((fn [n] (fn [x] (reduce * 1 (repeat n x)))) 2) 16), (((fn [n] (fn [x] (reduce * 1 (repeat n x)))) 8) 2)))

(defn solve2 []
  (= [1 8 27 64] (map ((fn [n] (fn [x] (reduce * 1 (repeat n x)))) 3) [1 2 3 4])))

(defn solve3 []
  (= [1 2 4 8 16] (map #(((fn [n] (fn [x] (reduce * 1 (repeat n x)))) %) 2) [0 1 2 3 4])))
