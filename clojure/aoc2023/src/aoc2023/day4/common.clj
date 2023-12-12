(ns aoc2023.day4.common
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [[_ numbers] (str/split line #":")
        [winning-numbers having-numbers] (-> numbers
                                             (str/split #"\|")
                                             (#(map str/trim %))
                                             (#(map (fn [numbers] (str/split numbers #"\s+")) %)))]
    {:winning-numbers winning-numbers, :having-numbers having-numbers}))

(defn count-matching-numbers [{winning-numbers :winning-numbers having-numbers :having-numbers}]
  (->> having-numbers
       (filter (set winning-numbers))
       (count)))
