(ns training.week2.day4.problem88-test
  (:require [clojure.test :refer :all]
            [training.week2.day4.problem88 :refer [solve1 solve2 solve3 solve4]]))

(deftest solve-test
  (testing "Week2 Day4 Problem88 - 1"
    (is (solve1)))
  (testing "Week2 Day4 Problem88 - 2"
    (is (solve2)))
  (testing "Week2 Day4 Problem88 - 3"
    (is (solve3)))
  (testing "Week2 Day4 Problem88 - 4"
    (is (solve4))))
