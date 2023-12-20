(ns training.week2.day1.problem29)

(defn solve1 []
  (= (#(->> %1 (re-seq #"[A-Z]") (apply str)) "HeLlO, WoRlD!") "HLOWRD"))

(defn solve2 []
  (empty? (#(->> %1 (re-seq #"[A-Z]") (apply str)) "nothing")))

(defn solve3 []
  (= (#(->> %1 (re-seq #"[A-Z]") (apply str)) "$#A(*&987Zf") "AZ"))
