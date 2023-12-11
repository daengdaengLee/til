(ns aoc2023.day3.common
  (:require [clojure.string :as str]))

(defn parse-lines [lines]
  (vec (map #(str/split % #"") lines)))

(defn is-digit? [s]
  (->> s
       (re-matches #"^[0-9]$")
       (nil?)
       (not)))

(defn find-digit-idx [line start-idx]
  (loop [idx start-idx]
    (cond
      (>= idx (count line)) -1
      (is-digit? (nth line idx)) idx
      :else (recur (+ idx 1)))))

(defn find-non-digit-idx [line start-idx]
  (loop [idx start-idx]
    (cond
      (>= idx (count line)) -1
      (not (is-digit? (nth line idx))) idx
      :else (recur (+ idx 1)))))

(defn parse-location [line start-idx]
  (let [idx1 (find-digit-idx line start-idx)
        idx2 (if (= idx1 -1) -1 (find-non-digit-idx line (+ idx1 1)))]
    (cond
      (= idx1 -1) nil
      (= idx2 -1) [idx1 (- (count line) 1)]
      :else [idx1 (- idx2 1)])))

(defn parse-locations [line]
  (loop [locations []
         idx 0]
    (if (>= idx (count line))
      locations
      (let [location (parse-location line idx)]
        (if (nil? location)
          locations
          (recur (conj locations location) (+ (nth location 1) 1)))))))

(defn parse-all-locations [lines]
  (map parse-locations lines))

(defn parse-num [line location]
  (let [[left-location right-location] location
        count (+ (- right-location left-location) 1)]
    (->> line
         (drop left-location)
         (take count)
         (str/join)
         (Integer/parseInt))))
