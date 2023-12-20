(ns training.week2.day1.problem29-test
  (:require [clojure.test :refer :all]
            [training.week2.day1.problem29 :refer [solve1 solve2 solve3]]))

(deftest solve-test
  (testing "Week2 Day1 Problem29 - 1"
    (is (solve1)))
  (testing "Week2 Day1 Problem29 - 2"
    (is (solve2)))
  (testing "Week2 Day1 Problem29 - 3"
    (is (solve3))))
