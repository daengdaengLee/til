(ns training.week1.day5.problem33-test
  (:require [clojure.test :refer :all]
            [training.week1.day5.problem33 :refer [solve1 solve2 solve3 solve4 solve5]]))

(deftest solve1-test
  (testing "Week1 Day5 Problem33 - 1"
    (is (solve1)))
  (testing "Week1 Day5 Problem33 - 2"
    (is (solve2)))
  (testing "Week1 Day5 Problem33 - 3"
    (is (solve3)))
  (testing "Week1 Day5 Problem33 - 4"
    (is (solve4)))
  (testing "Week1 Day5 Problem33 - 5"
    (is (solve5))))
