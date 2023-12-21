(ns training.week2.day2.problem83)

(defn solve1 []
  (= false ((fn [& args] (boolean (and (some true? args) (not (every? true? args))))) false false)))

(defn solve2 []
  (= true ((fn [& args] (boolean (and (some true? args) (not (every? true? args))))) true false)))

(defn solve3 []
  (= false ((fn [& args] (boolean (and (some true? args) (not (every? true? args))))) true)))

(defn solve4 []
  (= true ((fn [& args] (boolean (and (some true? args) (not (every? true? args))))) false true false)))

(defn solve5 []
  (= false ((fn [& args] (boolean (and (some true? args) (not (every? true? args))))) true true true)))

(defn solve6 []
  (= true ((fn [& args] (boolean (and (some true? args) (not (every? true? args))))) true true true false)))