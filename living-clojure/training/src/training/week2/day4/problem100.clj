(ns training.week2.day4.problem100)

(defn solve1 []
  (== ((fn [& nums]
         (letfn [(gcd [a b] (if (zero? b) a (recur b (mod a b))))
                 (lcm [a b] (/ (* a b) (gcd a b)))]
           (reduce lcm nums))) 2 3) 6))

(defn solve2 []
  (== ((fn [& nums]
         (letfn [(gcd [a b] (if (zero? b) a (recur b (mod a b))))
                 (lcm [a b] (/ (* a b) (gcd a b)))]
           (reduce lcm nums))) 5 3 7) 105))

(defn solve3 []
  (== ((fn [& nums]
         (letfn [(gcd [a b] (if (zero? b) a (recur b (mod a b))))
                 (lcm [a b] (/ (* a b) (gcd a b)))]
           (reduce lcm nums))) 1/3 2/5) 2))

(defn solve4 []
  (== ((fn [& nums]
         (letfn [(gcd [a b] (if (zero? b) a (recur b (mod a b))))
                 (lcm [a b] (/ (* a b) (gcd a b)))]
           (reduce lcm nums))) 3/4 1/6) 3/2))

(defn solve5 []
  (== ((fn [& nums]
         (letfn [(gcd [a b] (if (zero? b) a (recur b (mod a b))))
                 (lcm [a b] (/ (* a b) (gcd a b)))]
           (reduce lcm nums))) 7 5/7 2 3/5) 210))
