(ns aoc2023.day1.part1
  (:require [clojure.string :as str]))

(defn is-digit [s]
  (or (= s "0")
      (= s "1")
      (= s "2")
      (= s "3")
      (= s "4")
      (= s "5")
      (= s "6")
      (= s "7")
      (= s "8")
      (= s "9")
      false))

(defn pick-both-end [coll]
  (let [a (first coll)
        b (last coll)]
    (list a b)))

(defn parse-line [line]
  (-> line
      (str/split #"")
      (#(filter is-digit %))
      (pick-both-end)
      (#(str/join "" %))
      (Integer/parseInt)))

(defn solve []
  (with-open [r (clojure.java.io/reader "src/aoc2023/day1/input")]
    (-> r
        (line-seq)
        (#(map parse-line %))
        (#(reduce + %)))))
