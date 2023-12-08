(ns aoc2023.day2.part2
  (:require [aoc2023.day2.common :refer [solve-template]]))

(defn calculate-power [game]
  (-> game
      (:cubes)
      (vals)
      (#(reduce * 1 %))))

(defn process-games [games]
  (map calculate-power games))

(defn solve []
  (solve-template process-games))
