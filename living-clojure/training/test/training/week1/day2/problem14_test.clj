(ns training.week1.day2.problem14-test
  (:require [clojure.test :refer :all]
            [training.week1.day2.problem14 :refer [solve1, solve2, solve3, solve4]]))

(deftest solve1-test
  (testing "Week1 Day2 Problem14 - 1"
    (is (solve1)))
  (testing "Week1 Day2 Problem14 - 2"
    (is (solve2)))
  (testing "Week1 Day2 Problem14 - 3"
    (is (solve3)))
  (testing "Week1 Day2 Problem14 - 4"
    (is (solve4))))
