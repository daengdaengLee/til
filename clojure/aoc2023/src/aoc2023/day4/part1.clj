(ns aoc2023.day4.part1
  (:require [aoc2023.common :as common]
            [aoc2023.day4.common :refer [count-matching-numbers parse-line]]
            [clojure.math :as math]))

(defn solve []
  (->> "src/aoc2023/day4/input"
       (common/read-file-to-lines)
       (map parse-line)
       (map count-matching-numbers)
       (map (fn [count]
              (if (= count 0)
                0
                (int (math/pow 2 (- count 1))))))
       (reduce + 0)))
