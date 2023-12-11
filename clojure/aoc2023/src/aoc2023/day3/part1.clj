(ns aoc2023.day3.part1
  (:require [aoc2023.common :as common]
            [aoc2023.day3.common :refer [is-digit? parse-all-locations parse-lines parse-num]]))

(defn find-surrounds [lines idx location]
  (let [is (map #(+ idx %) [-1 0 1])
        [left-location right-location] location
        start-j (- left-location 1)
        end-j (+ right-location 1)
        js (range start-j (+ end-j 1))
        ijs (for [i is
                  j js]
              [i j])]
    (->> ijs
         (filter (fn [[i j]] (not (and (= idx i) (>= j left-location) (<= j right-location)))))
         (map (fn [[i j]] (nth (nth lines i []) j nil)))
         (filter #(not (nil? %))))))

(defn is-symbol? [s]
  (not (or (is-digit? s) (= "." s))))

(defn solve []
  (let [lines (parse-lines (common/read-file-to-lines "src/aoc2023/day3/input"))
        all-locations (parse-all-locations lines)]
    (->> lines
         (map-indexed (fn [idx line] {:idx idx, :line line}))
         (map (fn [entry]
                (map
                  (fn [location] (conj entry {:location location}))
                  (nth all-locations (:idx entry)))))
         (flatten)
         (map (fn [{idx :idx, line :line, location :location}]
                (let [surrounds (find-surrounds lines idx location)]
                  (when (some is-symbol? surrounds)
                    (parse-num line location)))))
         (filter #(not (nil? %)))
         (reduce + 0))))
