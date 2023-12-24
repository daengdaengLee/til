(ns training.week2.day4.problem100-test
  (:require [clojure.test :refer :all]
            [training.week2.day4.problem100 :refer [solve1 solve2 solve3 solve4 solve5]]))

(deftest solve-test
  (testing "Week2 Day4 Problem100 - 1"
    (is (solve1)))
  (testing "Week2 Day4 Problem100 - 2"
    (is (solve2)))
  (testing "Week2 Day4 Problem100 - 3"
    (is (solve3)))
  (testing "Week2 Day4 Problem100 - 4"
    (is (solve4)))
  (testing "Week2 Day4 Problem100 - 5"
    (is (solve5))))
