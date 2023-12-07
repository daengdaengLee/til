(ns aoc2023.day1.part2
  (:require [aoc2023.day1.common :refer [pick-both-end, solve-template]]
            [clojure.string :as str]))

(defn map-to-digits [line]
  (loop [digits []
         rest-line line]
    (cond
      (empty? rest-line) digits
      (= "0" (first rest-line)) (recur (conj digits 0) (subvec rest-line 1))
      (= "1" (first rest-line)) (recur (conj digits 1) (subvec rest-line 1))
      (= "2" (first rest-line)) (recur (conj digits 2) (subvec rest-line 1))
      (= "3" (first rest-line)) (recur (conj digits 3) (subvec rest-line 1))
      (= "4" (first rest-line)) (recur (conj digits 4) (subvec rest-line 1))
      (= "5" (first rest-line)) (recur (conj digits 5) (subvec rest-line 1))
      (= "6" (first rest-line)) (recur (conj digits 6) (subvec rest-line 1))
      (= "7" (first rest-line)) (recur (conj digits 7) (subvec rest-line 1))
      (= "8" (first rest-line)) (recur (conj digits 8) (subvec rest-line 1))
      (= "9" (first rest-line)) (recur (conj digits 9) (subvec rest-line 1))
      (= "9" (first rest-line)) (recur (conj digits 9) (subvec rest-line 1))
      (and
        (= "z" (nth rest-line 0 nil))
        (= "e" (nth rest-line 1 nil))
        (= "r" (nth rest-line 2 nil))
        (= "o" (nth rest-line 3 nil))) (recur (conj digits 0) (subvec rest-line 3))
      (and
        (= "o" (nth rest-line 0 nil))
        (= "n" (nth rest-line 1 nil))
        (= "e" (nth rest-line 2 nil))) (recur (conj digits 1) (subvec rest-line 2))
      (and
        (= "t" (nth rest-line 0 nil))
        (= "w" (nth rest-line 1 nil))
        (= "o" (nth rest-line 2 nil))) (recur (conj digits 2) (subvec rest-line 2))
      (and
        (= "t" (nth rest-line 0 nil))
        (= "h" (nth rest-line 1 nil))
        (= "r" (nth rest-line 2 nil))
        (= "e" (nth rest-line 3 nil))
        (= "e" (nth rest-line 4 nil))) (recur (conj digits 3) (subvec rest-line 4))
      (and
        (= "f" (nth rest-line 0 nil))
        (= "o" (nth rest-line 1 nil))
        (= "u" (nth rest-line 2 nil))
        (= "r" (nth rest-line 3 nil))) (recur (conj digits 4) (subvec rest-line 3))
      (and
        (= "f" (nth rest-line 0 nil))
        (= "i" (nth rest-line 1 nil))
        (= "v" (nth rest-line 2 nil))
        (= "e" (nth rest-line 3 nil))) (recur (conj digits 5) (subvec rest-line 3))
      (and
        (= "s" (nth rest-line 0 nil))
        (= "i" (nth rest-line 1 nil))
        (= "x" (nth rest-line 2 nil))) (recur (conj digits 6) (subvec rest-line 2))
      (and
        (= "s" (nth rest-line 0 nil))
        (= "e" (nth rest-line 1 nil))
        (= "v" (nth rest-line 2 nil))
        (= "e" (nth rest-line 3 nil))
        (= "n" (nth rest-line 4 nil))) (recur (conj digits 7) (subvec rest-line 4))
      (and
        (= "e" (nth rest-line 0 nil))
        (= "i" (nth rest-line 1 nil))
        (= "g" (nth rest-line 2 nil))
        (= "h" (nth rest-line 3 nil))
        (= "t" (nth rest-line 4 nil))) (recur (conj digits 8) (subvec rest-line 4))
      (and
        (= "n" (nth rest-line 0 nil))
        (= "i" (nth rest-line 1 nil))
        (= "n" (nth rest-line 2 nil))
        (= "e" (nth rest-line 3 nil))) (recur (conj digits 9) (subvec rest-line 3))
      :else (recur digits (subvec rest-line 1)))))

(defn parse-line [line]
  (-> line
      (str/split #"")
      (map-to-digits)
      (pick-both-end)
      (#(+ (* 10 (first %)) (last %)))))

(defn solve []
  (solve-template parse-line))
