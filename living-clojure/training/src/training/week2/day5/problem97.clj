(ns training.week2.day5.problem97)

(defn solve1 []
  (= (#(letfn [(pairs [coll] (lazy-seq (let [a (first coll) b (second coll) coll (rest coll)]
                                         (when (every? (complement nil?) [a b]) (cons [a b] (pairs coll))))))
               (insert-ones [coll] (concat (cons 1 coll) [1]))
               (next-line [coll] (->> coll (pairs) (map (fn [[a b]] (+ a b))) (insert-ones)))]
         (case %1
           1 [1]
           2 [1 1]
           (reduce (fn [coll _] (next-line coll)) [1 1] (repeat (- %1 2) 0)))) 1)
     [1]))

(defn solve2 []
  (= (map
       #(letfn [(pairs [coll] (lazy-seq (let [a (first coll) b (second coll) coll (rest coll)]
                                          (when (every? (complement nil?) [a b]) (cons [a b] (pairs coll))))))
                (insert-ones [coll] (concat (cons 1 coll) [1]))
                (next-line [coll] (->> coll (pairs) (map (fn [[a b]] (+ a b))) (insert-ones)))]
          (case %1
            1 [1]
            2 [1 1]
            (reduce (fn [coll _] (next-line coll)) [1 1] (repeat (- %1 2) 0))))
       (range 1 6))
     [[1]
      [1 1]
      [1 2 1]
      [1 3 3 1]
      [1 4 6 4 1]]))

(defn solve3 []
  (=
    (#(letfn [(pairs [coll] (lazy-seq (let [a (first coll) b (second coll) coll (rest coll)]
                                        (when (every? (complement nil?) [a b]) (cons [a b] (pairs coll))))))
              (insert-ones [coll] (concat (cons 1 coll) [1]))
              (next-line [coll] (->> coll (pairs) (map (fn [[a b]] (+ a b))) (insert-ones)))]
        (case %1
          1 [1]
          2 [1 1]
          (reduce (fn [coll _] (next-line coll)) [1 1] (repeat (- %1 2) 0)))) 11)
    [1 10 45 120 210 252 210 120 45 10 1]))
