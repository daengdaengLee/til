(ns macro.core
  (:gen-class))

(defn hi-queen [phrase]
  (str phrase ", so please your Majesty."))

;(defmacro def-hi-queen [name phrase]
;  (list 'defn
;        (symbol name)
;        []
;        (list 'hi-queen phrase)))
(defmacro def-hi-queen [name phrase]
  `(defn ~(symbol name) []
     (hi-queen ~phrase)))

;(defn alice-hi-queen []
;  (hi-queen "My name is Alice"))
(def-hi-queen alice-hi-queen "My name is Alice")

;(defn march-hare-hi-queen []
;  (hi-queen "I'm the March Hare"))
(def-hi-queen march-hare-hi-queen "I'm the March Hare")

;(defn white-rabbit-hi-queen []
;  (hi-queen "I'm the White Rabbit"))
(def-hi-queen white-rabbit-hi-queen "I'm the White Rabbit")

;(defn mad-hatter-hi-queen []
;  (hi-queen "I'm the Mad Hatter"))
(def-hi-queen mad-hatter-hi-queen "I'm the Mad Hatter")

(defn -main [& args]
  (println (alice-hi-queen))
  (println (march-hare-hi-queen))
  (println (white-rabbit-hi-queen))
  (println (mad-hatter-hi-queen)))
