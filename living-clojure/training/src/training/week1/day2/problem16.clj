(ns training.week1.day2.problem16)

(defn solve1 []
  (= (#(str "Hello, " % "!") "Dave") "Hello, Dave!"))

(defn solve2 []
  (= (#(str "Hello, " % "!") "Jenn") "Hello, Jenn!"))

(defn solve3 []
  (= (#(str "Hello, " % "!") "Rhea") "Hello, Rhea!"))
