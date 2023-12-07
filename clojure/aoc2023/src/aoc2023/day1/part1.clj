(ns aoc2023.day1.part1
  (:require [aoc2023.day1.common :refer [pick-both-end, solve-template]]
            [clojure.string :as str]))

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

(defn parse-line [line]
  (-> line
      (str/split #"")
      (#(filter is-digit %))
      (pick-both-end)
      (#(str/join "" %))
      (Integer/parseInt)))

(defn solve []
  (solve-template parse-line))
