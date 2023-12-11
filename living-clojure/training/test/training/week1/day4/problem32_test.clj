(ns training.week1.day4.problem32-test
  (:require [clojure.test :refer :all]
            [training.week1.day4.problem32 :refer [solve1 solve2 solve3 solve4]]))

(deftest solve-test
  (testing "Week1 Day3 Problem32 - 1"
    (is (solve1)))
  (testing "Week1 Day3 Problem32 - 2"
    (is (solve2)))
  (testing "Week1 Day3 Problem32 - 3"
    (is (solve3)))
  (testing "Week1 Day3 Problem32 - 4"
    (is (solve4))))
