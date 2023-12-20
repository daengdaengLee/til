(ns training.week1.day5.problem30-test
  (:require [clojure.test :refer :all]
            [training.week1.day5.problem30 :refer [solve1 solve2 solve3]]))

(deftest solve-test
  (testing "Week1 Day5 Problem30 - 1"
    (is (solve1)))
  (testing "Week1 Day5 Problem30 - 2"
    (is (solve2)))
  (testing "Week1 Day5 Problem30 - 3"
    (is (solve3))))
