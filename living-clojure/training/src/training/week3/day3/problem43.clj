(ns training.week3.day3.problem43)

(defn reverse-interleave [coll n]
  (letfn [(append [[coll v]]
            (if (nil? v) coll (concat coll [v])))
          (distribute [coll result]
            (->> (concat coll (repeat nil)) (map vector result) (map append)))]
    (loop [coll coll
           result (repeat n '())]
      (if (empty? coll)
        result
        (recur
          (drop n coll)
          (distribute coll result))))))

(defn solve1 []
  (= (reverse-interleave [1 2 3 4 5 6] 2) '((1 3 5) (2 4 6))))

(defn solve2 []
  (= (reverse-interleave (range 9) 3) '((0 3 6) (1 4 7) (2 5 8))))

(defn solve3 []
  (= (reverse-interleave (range 10) 5) '((0 5) (1 6) (2 7) (3 8) (4 9))))
