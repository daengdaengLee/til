(ns training.week2.day1.problem42)

(defn solve1 []
  (= (#(loop [n % result 1] (if (= n 1) result (recur (- n 1) (* result n)))) 1) 1))

(defn solve2 []
  (= (#(loop [n % result 1] (if (= n 1) result (recur (- n 1) (* result n)))) 3) 6))

(defn solve3 []
  (= (#(loop [n % result 1] (if (= n 1) result (recur (- n 1) (* result n)))) 5) 120))

(defn solve4 []
  (= (#(loop [n % result 1] (if (= n 1) result (recur (- n 1) (* result n)))) 8) 40320))
