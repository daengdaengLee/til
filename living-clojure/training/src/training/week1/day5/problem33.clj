(ns training.week1.day5.problem33)

(defn solve1 []
  (=
    (#(loop [coll %1 result []] (if (empty? coll) result (recur (drop 1 coll) (concat result (repeat %2 (first coll)))))) [1 2 3] 2)
    '(1 1 2 2 3 3)))

(defn solve2 []
  (= (#(loop [coll %1 result []] (if (empty? coll) result (recur (drop 1 coll) (concat result (repeat %2 (first coll)))))) [:a :b] 4)
     '(:a :a :a :a :b :b :b :b)))

(defn solve3 []
  (= (#(loop [coll %1 result []] (if (empty? coll) result (recur (drop 1 coll) (concat result (repeat %2 (first coll)))))) [4 5 6] 1)
     '(4 5 6)))

(defn solve4 []
  (= (#(loop [coll %1 result []] (if (empty? coll) result (recur (drop 1 coll) (concat result (repeat %2 (first coll)))))) [[1 2] [3 4]] 2)
     '([1 2] [1 2] [3 4] [3 4])))

(defn solve5 []
  (= (#(loop [coll %1 result []] (if (empty? coll) result (recur (drop 1 coll) (concat result (repeat %2 (first coll)))))) [44 33] 2)
     [44 44 33 33]))
