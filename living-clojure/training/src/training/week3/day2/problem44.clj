(ns training.week3.day2.problem44)

(defn rotate [n coll]
  (let [l (count coll)
        n (loop [n n]
            (cond
              (< n 0) (recur (+ n l))
              (>= n l) (mod n l)
              :else n))
        sub-a (drop n coll)
        sub-b (take n coll)]
    (concat sub-a sub-b)))

(defn solve1 []
  (= (rotate 2 [1 2 3 4 5]) '(3 4 5 1 2)))

(defn solve2 []
  (= (rotate -2 [1 2 3 4 5]) '(4 5 1 2 3)))

(defn solve3 []
  (= (rotate 6 [1 2 3 4 5]) '(2 3 4 5 1)))

(defn solve4 []
  (= (rotate 1 '(:a :b :c)) '(:b :c :a)))

(defn solve5 []
  (= (rotate -4 '(:a :b :c)) '(:c :a :b)))
