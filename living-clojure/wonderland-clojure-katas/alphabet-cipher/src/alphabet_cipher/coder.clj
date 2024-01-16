(ns alphabet-cipher.coder
  (:require [clojure.string :as string]))

(def row "abcdefghijklmnopqrstuvwxyz")

(def row-count (count row))

(defn index-in-tokens [token tokens]
  (loop [tokens tokens
         idx 0]
      (cond
        (empty? tokens) -1
        (= (first tokens) token) idx
        :else (recur (rest tokens) (+ idx 1)))))

(defn index-in-row [token]
  (index-in-tokens token (seq row)))

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
  (->>
    (zip-with-keyword keyword, message)
    (map (fn [[a b]]
      (let [dropped-row (drop-row a (seq row))
            index-in-dropped-row (index-in-tokens b dropped-row )
            original-token (first (drop index-in-dropped-row (seq row)))]
        original-token)))
     (string/join "")))

(defn decipher [cipher message]
  "decypherme")

