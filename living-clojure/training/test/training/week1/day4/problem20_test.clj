(ns training.week1.day4.problem20-test
  (:require [clojure.test :refer :all]
            [training.week1.day4.problem20 :refer [solve1 solve2 solve3]]))

(deftest solve-test
  (testing "Week1 Day4 Problem20 - 1"
    (is (solve1)))
  (testing "Week1 Day4 Problem20 - 2"
    (is (solve2)))
  (testing "Week1 Day4 Problem20 - 3"
    (is (solve3))))
