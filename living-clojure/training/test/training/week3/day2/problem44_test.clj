(ns training.week3.day2.problem44-test
  (:require [clojure.test :refer :all]
            [training.week3.day2.problem44 :refer [solve1 solve2 solve3 solve4 solve5]]))

(deftest solve-test
  (testing "Week3 Day2 Problem44 - 1"
    (is (solve1)))
  (testing "Week3 Day2 Problem44 - 2"
    (is (solve2)))
  (testing "Week3 Day2 Problem44 - 3"
    (is (solve3)))
  (testing "Week3 Day2 Problem44 - 4"
    (is (solve4)))
  (testing "Week3 Day2 Problem44 - 5"
    (is (solve5))))
