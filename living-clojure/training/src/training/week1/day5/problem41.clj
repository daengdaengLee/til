(ns training.week1.day5.problem41)

(defn solve1 []
  (= (#(loop [coll %1 n %2 result []] (if (empty? coll) result (recur (drop n coll) n (concat result (take (- n 1) coll)))))
       [1 2 3 4 5 6 7 8]
       3)
     [1 2 4 5 7 8]))

(defn solve2 []
  (= (#(loop [coll %1 n %2 result []] (if (empty? coll) result (recur (drop n coll) n (concat result (take (- n 1) coll)))))
       [:a :b :c :d :e :f]
       2)
     [:a :c :e]))

(defn solve3 []
  (= (#(loop [coll %1 n %2 result []] (if (empty? coll) result (recur (drop n coll) n (concat result (take (- n 1) coll)))))
       [1 2 3 4 5 6]
       4)
     [1 2 3 5 6]))
