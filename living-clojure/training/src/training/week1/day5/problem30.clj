(ns training.week1.day5.problem30)

(defn solve1 []
  (= (apply str (reduce #(if (= (last %1) %2) %1 (vec (conj %1 %2))) [] "Leeeeeerrroyyy")) "Leroy"))

(defn solve2 []
  (= (reduce #(if (= (last %1) %2) %1 (vec (conj %1 %2))) [] [1 1 2 3 3 2 2 3]) '(1 2 3 2 3)))

(defn solve3 []
  (= (reduce #(if (= (last %1) %2) %1 (vec (conj %1 %2))) [] [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2])))
