(ns training.week1.day2.problem15-test
  (:require [clojure.test :refer :all]
            [training.week1.day2.problem15 :refer [solve1, solve2, solve3, solve4]]))

(deftest solve-test
  (testing "Week1 Day2 Problem15 - 1"
    (is (solve1)))
  (testing "Week1 Day2 Problem15 - 2"
    (is (solve2)))
  (testing "Week1 Day2 Problem15 - 3"
    (is (solve3)))
  (testing "Week1 Day2 Problem15 - 4"
    (is (solve4))))
