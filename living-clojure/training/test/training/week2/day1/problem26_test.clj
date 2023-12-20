(ns training.week2.day1.problem26-test
  (:require [clojure.test :refer :all]
            [training.week2.day1.problem26 :refer [solve1 solve2 solve3]]))

(deftest solve-test
  (testing "Week2 Day1 Problem26 - 1"
    (is (solve1)))
  (testing "Week2 Day1 Problem26 - 2"
    (is (solve2)))
  (testing "Week2 Day1 Problem26 - 3"
    (is (solve3))))
