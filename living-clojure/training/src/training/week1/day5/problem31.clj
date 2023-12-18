(ns training.week1.day5.problem31)

(defn solve1 []
  (= (reduce
       (fn [acc cur]
         (let [last-sub-coll (last acc)]
           (cond
             (nil? last-sub-coll) (list (list cur))
             (= (last last-sub-coll) cur) (concat (drop-last acc) (list (concat last-sub-coll (list cur))))
             :else (concat acc (list (list cur))))))
       []
       [1 1 2 1 1 1 3 3])
     '((1 1) (2) (1 1 1) (3 3))))

(defn solve2 []
  (= (reduce
       (fn [acc cur]
         (let [last-sub-coll (last acc)]
           (cond
             (nil? last-sub-coll) (list (list cur))
             (= (last last-sub-coll) cur) (concat (drop-last acc) (list (concat last-sub-coll (list cur))))
             :else (concat acc (list (list cur))))))
       []
       [:a :a :b :b :c])
     '((:a :a) (:b :b) (:c))))

(defn solve3 []
  (= (reduce
       (fn [acc cur]
         (let [last-sub-coll (last acc)]
           (cond
             (nil? last-sub-coll) (list (list cur))
             (= (last last-sub-coll) cur) (concat (drop-last acc) (list (concat last-sub-coll (list cur))))
             :else (concat acc (list (list cur))))))
       []
       [[1 2] [1 2] [3 4]])
     '(([1 2] [1 2]) ([3 4]))))
