(ns training.week3.day1.problem95)

(defn is-tree? [coll]
  (letfn [(is-tree-node? [node]
            (and (sequential? node)
                 (not (nil? (first node)))
                 (= 2 (count (vec (take 2 (rest node)))))
                 (empty? (drop 3 node))))
          (get-children [[_ left right]] (filter (complement nil?) [left right]))]
    (loop [q [coll]]
      (let [current (first q)]
        (cond
          (empty? q) true
          (is-tree-node? current) (recur (concat (rest q) (get-children current)))
          :else false)))))

(defn solve1 []
  (= (is-tree? '(:a (:b nil nil) nil))
     true))

(defn solve2 []
  (= (is-tree? '(:a (:b nil nil)))
     false))

(defn solve3 []
  (= (is-tree? [1 nil [2 [3 nil nil] [4 nil nil]]])
     true))

(defn solve4 []
  (= (is-tree? [1 [2 nil nil] [3 nil nil] [4 nil nil]])
     false))

(defn solve5 []
  (= (is-tree? [1 [2 [3 [4 nil nil] nil] nil] nil])
     true))

(defn solve6 []
  (= (is-tree? [1 [2 [3 [4 false nil] nil] nil] nil])
     false))

(defn solve7 []
  (= (is-tree? '(:a nil ()))
     false))
