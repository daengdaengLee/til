(ns training.week1.day2.problem16-test
  (:require [clojure.test :refer :all]
            [training.week1.day2.problem16 :refer [solve1, solve2, solve3]]))

(deftest solve1-test
  (testing "Week1 Day2 Problem16 - 1"
    (is (solve1)))
  (testing "Week1 Day2 Problem16 - 2"
    (is (solve2)))
  (testing "Week1 Day2 Problem16 - 3"
    (is (solve3))))
