(ns aoc2023.day1.common
  (:require [aoc2023.common :as common]))

(defn solve-template [parse-line]
  (-> "src/aoc2023/day1/input"
      (common/read-file-to-lines)
      (#(map parse-line %))
      (#(reduce + %))))

(defn pick-both-end [coll]
  (let [a (first coll)
        b (last coll)]
    (list a b)))
