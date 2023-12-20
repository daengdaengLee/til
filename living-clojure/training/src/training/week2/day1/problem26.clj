(ns training.week2.day1.problem26)

(defn solve1 []
  (= (#(case %
         0 '()
         1 (list 1)
         2 (list 1 2)
         (loop [n (- % 2)
                result (list 1 1)]
           (if (= n 0)
             result
             (let [a (last result)
                   b (->> result (drop-last 1) (last))]
               (recur (- n 1) (concat result [(+ a b)])))))) 3)
     '(1 1 2)))

(defn solve2 []
  (= (#(case %
         0 '()
         1 (list 1)
         2 (list 1 2)
         (loop [n (- % 2)
                result (list 1 1)]
           (if (= n 0)
             result
             (let [a (last result)
                   b (->> result (drop-last 1) (last))]
               (recur (- n 1) (concat result [(+ a b)])))))) 6)
     '(1 1 2 3 5 8)))

(defn solve3 []
  (= (#(case %
         0 '()
         1 (list 1)
         2 (list 1 2)
         (loop [n (- % 2)
                result (list 1 1)]
           (if (= n 0)
             result
             (let [a (last result)
                   b (->> result (drop-last 1) (last))]
               (recur (- n 1) (concat result [(+ a b)])))))) 8)
     '(1 1 2 3 5 8 13 21)))
