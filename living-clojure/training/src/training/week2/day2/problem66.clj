(ns training.week2.day2.problem66)

(defn solve1 []
  (= (#(loop [a (max %1 %2) b (min %1 %2)] (let [r (mod a b)] (if (= r 0) b (recur b r)))) 2 4) 2))

(defn solve2 []
  (= (#(loop [a (max %1 %2) b (min %1 %2)] (let [r (mod a b)] (if (= r 0) b (recur b r)))) 10 5) 5))

(defn solve3 []
  (= (#(loop [a (max %1 %2) b (min %1 %2)] (let [r (mod a b)] (if (= r 0) b (recur b r)))) 5 7) 1))

(defn solve4 []
  (= (#(loop [a (max %1 %2) b (min %1 %2)] (let [r (mod a b)] (if (= r 0) b (recur b r)))) 1023 858) 33))
