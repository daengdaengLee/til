(ns alphabet-cipher.coder
  (:require [clojure.string :as string]))

(def row "abcdefghijklmnopqrstuvwxyz")

(def row-count (count row))

(defn index-in-row [token]
  (loop [tokens (seq row)
         idx 0]
    (cond
      (empty? tokens) -1
      (= (first tokens) token) idx
      :else (recur (rest tokens) (+ idx 1)))))

(defn repeat-chars [s]
  (flatten (repeat (seq s))))

(defn zip-with-keyword [keyword message]
  (map vector (repeat-chars keyword) (seq message)))

(defn drop-row [token row]
  (drop (index-in-row token) (repeat-chars row)))

(defn encode [keyword message]
  (->>
    (zip-with-keyword keyword message)
    (map (fn [[a b]] (first (drop-row b (drop-row a (seq row))))))
    (string/join "")))

(defn decode [keyword message]
  "decodeme")

(defn decipher [cipher message]
  "decypherme")

