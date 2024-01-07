(ns training.week3.day4.problem67)

(defn is-prime? [n]
  (cond
    (not (integer? n)) false
    (< n 2) false
    (= n 2) true
    :else (every? #(not= 0 (mod n %1)) (range 2 n)) ))

(defn prime [n]
  (->> (iterate inc 2)
       (filter is-prime?)
       (take n)))

(defn solve1 []
  (= (prime 2) [2 3]))

(defn solve2 []
  (= (prime 5) [2 3 5 7 11]))

(defn solve3 []
  (= (last (prime 100)) 541))

