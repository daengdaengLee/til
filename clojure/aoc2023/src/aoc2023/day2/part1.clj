(ns aoc2023.day2.part1
  (:require [aoc2023.day2.common :refer [solve-template]]))

(defn is-possible-game? [game]
  (let [cubes (:cubes game)
        red (:red cubes)
        green (:green cubes)
        blue (:blue cubes)]
    (and (<= red 12) (<= green 13) (<= blue 14))))

(defn process-games [games]
  (-> games
      (#(filter is-possible-game? %))
      (#(map (fn [game] (:gameId game)) %))))

(defn solve []
  (solve-template process-games))
