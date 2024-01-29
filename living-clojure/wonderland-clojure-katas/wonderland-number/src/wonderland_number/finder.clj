(ns wonderland-number.finder)

(defn is-wonderland-number [num]
  (every?
    (fn [multiplier]
      (let [a num
            b (* num multiplier)
            a-set (set (str a))
            b-set (set (str b))]
        (and
          (not (= a b))
          (= a-set b-set))))
    [2 3 4 5 6]))

(defn wonderland-number []
  (loop [num 100000]
    (if (is-wonderland-number num)
      num
      (recur (+ num 1)))))
