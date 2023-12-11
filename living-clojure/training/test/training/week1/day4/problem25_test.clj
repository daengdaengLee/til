(ns training.week1.day4.problem25-test
  (:require [clojure.test :refer :all]
            [training.week1.day4.problem25 :refer [solve1 solve2 solve3 solve4]]))

(deftest solve-test
  (testing "Week1 Day4 Problem25 - 1"
    (is (solve1)))
  (testing "Week1 Day4 Problem25 - 2"
    (is (solve2)))
  (testing "Week1 Day4 Problem25 - 3"
    (is (solve3)))
  (testing "Week1 Day4 Problem25 - 4"
    (is (solve4))))
