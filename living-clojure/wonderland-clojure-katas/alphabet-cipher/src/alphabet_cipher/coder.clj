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

(defn token-at-index-in-row [index]
  (if (< index 0)
    (nth (seq row) (+ index row-count))
    (nth (seq row) index)))

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
            index-in-dropped-row (index-in-tokens b dropped-row)
            original-token (first (drop index-in-dropped-row (seq row)))]
        original-token)))
     (string/join "")))

(defn decipher [cipher message]
  (->>
    (zip-with-keyword cipher message)
    (map (fn [[a b]]
      (let [a-index (index-in-row a)
            b-index (index-in-row b)
            a-b (- a-index b-index)]
        (token-at-index-in-row a-b))))
    (reduce
      (fn [acc cur]
        (if (empty? acc)
          [[cur]]
          (let [sub-acc (last acc)
                start-token (first sub-acc)]
            (if (= cur start-token)
              (concat acc [[cur]])
              (concat (drop-last acc) [(vec (concat sub-acc [cur]))])))))
      [])
      (reduce
        (fn [acc cur]
          (cond
            (:done acc) acc
            (nil? (:first acc)) {:done false, :first cur, :acc [cur]}
            (= (:first acc) cur) (conj acc {:done true})
            :else (conj acc {:acc (conj (:acc acc) cur) })))
        {:done false, :first nil, :acc []})
      (:acc)
      (flatten)
      (string/join "")))

