(ns aoc2023.day1.common)

(defn solve-template [parse-line]
  (with-open [r (clojure.java.io/reader "src/aoc2023/day1/input")]
    (-> r
        (line-seq)
        (#(map parse-line %))
        (#(reduce + %)))))

(defn pick-both-end [coll]
  (let [a (first coll)
        b (last coll)]
    (list a b)))
