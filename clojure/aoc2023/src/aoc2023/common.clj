(ns aoc2023.common)

(defn read-file-to-lines [path]
  (with-open [r (clojure.java.io/reader path)]
    (-> r
        (line-seq)
        (doall))))
