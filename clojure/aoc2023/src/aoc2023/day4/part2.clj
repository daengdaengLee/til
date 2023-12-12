(ns aoc2023.day4.part2
  (:require [aoc2023.common :as common]
            [aoc2023.day4.common :refer [count-matching-numbers parse-line]]))

(defn solve []
  (->> "src/aoc2023/day4/input"
       (common/read-file-to-lines)
       (map parse-line)
       (map (fn [entry]
              (let [count (count-matching-numbers entry)]
                (conj entry {:matching-count count}))))
       (reduce
         (fn [{total-count :total-count, copy-counts :copy-counts}
              {matching-count :matching-count}]
           (let [copy-count (let [c (first copy-counts)]
                              (if (nil? c) 0 c))
                 current-count (+ 1 copy-count)
                 total-count (+ total-count current-count)
                 copy-counts (let [l (max matching-count (count copy-counts))
                                   copy-counts-1 (take l (concat (drop 1 copy-counts) (repeat 0)))
                                   copy-counts-2 (take l (concat (repeat matching-count current-count) (repeat 0)))]
                               (map + copy-counts-1 copy-counts-2))]
             {:total-count total-count, :copy-counts copy-counts}))
         {:total-count 0, :copy-counts []})
       (:total-count)))
