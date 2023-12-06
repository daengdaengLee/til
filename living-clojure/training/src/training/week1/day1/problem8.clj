(ns training.week1.day1.problem8
  (:require [clojure.set]))

(defn solve1 []
  (= #{:a :b :c :d} (set '(:a :a :b :c :c :c :c :d :d))))

(defn solve2 []
  (= #{:a :b :c :d} (clojure.set/union #{:a :b :c} #{:b :c :d})))
