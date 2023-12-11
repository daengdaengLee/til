(ns training.week1.day4.problem24-test
  (:require [clojure.test :refer :all]
            [training.week1.day4.problem24 :refer [solve1 solve2 solve3 solve4 solve5]]))

(deftest solve-test
  (testing "Week1 Day4 Problem24 - 1"
    (is (solve1)))
  (testing "Week1 Day4 Problem24 - 2"
    (is (solve2)))
  (testing "Week1 Day4 Problem24 - 3"
    (is (solve3)))
  (testing "Week1 Day4 Problem24 - 4"
    (is (solve4)))
  (testing "Week1 Day4 Problem24 - 5"
    (is (solve5))))
