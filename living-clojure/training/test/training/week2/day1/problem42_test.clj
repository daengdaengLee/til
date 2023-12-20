(ns training.week2.day1.problem42-test
  (:require [clojure.test :refer :all]
            [training.week2.day1.problem42 :refer [solve1 solve2 solve3 solve4]]))

(deftest solve-test
  (testing "Week2 Day1 Problem42 - 1"
    (is (solve1)))
  (testing "Week2 Day1 Problem42 - 2"
    (is (solve2)))
  (testing "Week2 Day1 Problem42 - 3"
    (is (solve3)))
  (testing "Week2 Day1 Problem42 - 4"
    (is (solve4))))
