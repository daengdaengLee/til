(ns training.week2.day4.problem88)

(defn solve1 []
  (= (#(let [union (set (concat %1 %2))
             intersection (set (filter %2 %1))]
         (set (filter (complement intersection) union))) #{1 2 3 4 5 6} #{1 3 5 7}) #{2 4 6 7}))

(defn solve2 []
  (= (#(let [union (set (concat %1 %2))
             intersection (set (filter %2 %1))]
         (set (filter (complement intersection) union))) #{:a :b :c} #{}) #{:a :b :c}))

(defn solve3 []
  (= (#(let [union (set (concat %1 %2))
             intersection (set (filter %2 %1))]
         (set (filter (complement intersection) union))) #{} #{4 5 6}) #{4 5 6}))

(defn solve4 []
  (= (#(let [union (set (concat %1 %2))
             intersection (set (filter %2 %1))]
         (set (filter (complement intersection) union))) #{[1 2] [2 3]} #{[2 3] [3 4]}) #{[1 2] [3 4]}))
