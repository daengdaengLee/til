(ns aoc2023.day2.common
  (:require [aoc2023.common :as common]
            [clojure.string :as str]))

(defn parse-game-id [token]
  (-> token
      (str/replace #"Game" "")
      (str/trim)
      (Integer/parseInt)))

(defn parse-cube-count [token]
  (let [[count-token color-token] (str/split token #"\s+")
        count (Integer/parseInt count-token)
        color (keyword color-token)]
    (hash-map color count)))

(defn parse-cube-counts [token]
  (-> token
      (str/split #",")
      (#(map str/trim %))
      (#(map parse-cube-count %))
      (#(reduce (fn [acc cur] (conj acc cur)) {} %))))

(defn merge-cube-counts [cube-counts-1 cube-counts-2]
  (let [red (max (get cube-counts-1 :red 0) (get cube-counts-2 :red 0))
        green (max (get cube-counts-1 :green 0) (get cube-counts-2 :green 0))
        blue (max (get cube-counts-1 :blue 0) (get cube-counts-2 :blue 0))]
    {:red red, :green green, :blue blue}))

(defn parse-max-cube-counts [token]
  (-> token
      (str/split #";")
      (#(map str/trim %))
      (#(map parse-cube-counts %))
      (#(reduce merge-cube-counts {:red 0, :green 0, :blue 0} %))))

(defn parse-game [line]
  (let [[token1 token2] (str/split line #":")
        gameId (parse-game-id token1)
        cubes (parse-max-cube-counts token2)]
    {:gameId gameId, :cubes cubes}))

(defn solve-template [f]
  (-> "src/aoc2023/day2/input"
      (common/read-file-to-lines)
      (#(map parse-game %))
      (f)
      (#(reduce + 0 %))))
