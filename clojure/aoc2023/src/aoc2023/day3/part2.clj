(ns aoc2023.day3.part2
  (:require [aoc2023.common :as common]
            [aoc2023.day3.common :refer [parse-all-locations parse-lines parse-num]]))

(defn is-star? [s]
  (= "*" s))

(defn make-surround-indexes [i j]
  (let [is (map #(+ i %) [-1 0 1])
        js (map #(+ j %) [-1 0 1])]
    (for [i is
          j js]
      [i j])))

(defn filter-all-locations-at-indexes [all-locations indexes]
  "all-locations:
  (
    [[0 2] ... [29 30]]
    [[9 11] ... [17 19]]
    ...
  )
  indexes:
  ([0 11] [0 12] ...)
  "
  (->> indexes
       (map (fn [[i j]]
              (->> (nth all-locations i [])
                   (filter (fn [[left right]] (and (>= j left) (<= j right))))
                   (map (fn [location] {:i i, :location location})))))
       (reduce concat nil)
       (set)))

(defn solve []
  (let [lines (parse-lines (common/read-file-to-lines "src/aoc2023/day3/input"))
        all-locations (vec (parse-all-locations lines))]
    (->> lines
         (map-indexed (fn [i line] {:i i, :line line}))
         (map (fn [{i :i, line :line}]
                (map-indexed (fn [j s] {:i i, :j j, :s s}) line)))
         (flatten)
         (filter (fn [{s :s}] (is-star? s)))
         (map (fn [{i :i, j :j}] {:i i, :j j, :surround-indexes (make-surround-indexes i j)}))
         (map (fn [{surround-indexes :surround-indexes}]
                (filter-all-locations-at-indexes all-locations surround-indexes)))
         (filter (fn [locations]
                   (-> locations
                       (count)
                       (= 2))))
         (map #(map (fn [{i :i, location :location}]
                      (let [line (nth lines i)]
                        (parse-num line location))) %))
         (map #(reduce * 1 %))
         (reduce + 0))))
