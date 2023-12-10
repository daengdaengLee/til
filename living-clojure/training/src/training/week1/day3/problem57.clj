(ns training.week1.day3.problem57)

; (conj (conj (conj (conj (conj nil 1) 2) 3) 4) 5)
; (5 4 3 2 1)
(defn solve []
  (= '(5 4 3 2 1) ((fn foo [x] (when (> x 0) (conj (foo (dec x)) x))) 5)))
