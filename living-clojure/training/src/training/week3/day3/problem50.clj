(ns training.week3.day3.problem50)

(defn split-by-type [coll]
  (vals (reduce
          (fn [acc cur]
            (let [t (type cur)
                  group (get acc t [])]
              (assoc acc t (concat group [cur]))))
          {}
          coll)))

(defn solve1 []
  (= (set (split-by-type [1 :a 2 :b 3 :c])) #{[1 2 3] [:a :b :c]}))

(defn solve2 []
  (= (set (split-by-type [:a "foo" "bar" :b])) #{[:a :b] ["foo" "bar"]}))

(defn solve3 []
  (= (set (split-by-type [[1 2] :a [3 4] 5 6 :b])) #{[[1 2] [3 4]] [:a :b] [5 6]}))
