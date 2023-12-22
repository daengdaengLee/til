(ns training.week2.day3.problem90)

(defn solve1 []
  (= (#(set (for [a %1 b %2] [a b])) #{"ace" "king" "queen"} #{"♠" "♥" "♦" "♣"})
     #{["ace" "♠"] ["ace" "♥"] ["ace" "♦"] ["ace" "♣"]
       ["king" "♠"] ["king" "♥"] ["king" "♦"] ["king" "♣"]
       ["queen" "♠"] ["queen" "♥"] ["queen" "♦"] ["queen" "♣"]}))

(defn solve2 []
  (= (#(set (for [a %1 b %2] [a b])) #{1 2 3} #{4 5})
     #{[1 4] [2 4] [3 4] [1 5] [2 5] [3 5]}))

(defn solve3 []
  (= 300 (count (#(set (for [a %1 b %2] [a b])) (into #{} (range 10))
                  (into #{} (range 30))))))
