(ns training.week3.day1.problem96)

(defn is-symmetry-tree? [tree]
  (letfn [(is-symmetry-nodes? [nodes]
            (let [values (map #(first %1) nodes)]
              (= values (reverse values))))
          (get-children [node] (rest node))
          (get-next-nodes [nodes]
            (apply concat (map #(get-children %1) nodes)))
          (is-symmetry-tree-internal? [nodes]
            (cond
              (empty? nodes) true
              (is-symmetry-nodes? nodes) (recur (get-next-nodes nodes))
              :else false))]
    (is-symmetry-tree-internal? [tree])))

(defn solve1 []
  (= (is-symmetry-tree? '(:a (:b nil nil) (:b nil nil))) true))

(defn solve2 []
  (= (is-symmetry-tree? '(:a (:b nil nil) nil)) false))

(defn solve3 []
  (= (is-symmetry-tree? '(:a (:b nil nil) (:c nil nil))) false))

(defn solve4 []
  (= (is-symmetry-tree? [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
                         [2 [3 nil [4 [6 nil nil] [5 nil nil]]] nil]])
     true))

(defn solve5 []
  (= (is-symmetry-tree? [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
                         [2 [3 nil [4 [5 nil nil] [6 nil nil]]] nil]])
     false))

(defn solve6 []
  (= (is-symmetry-tree? [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
                         [2 [3 nil [4 [6 nil nil] nil]] nil]])
     false))
