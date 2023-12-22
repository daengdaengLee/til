(ns training.week2.day3.problem90-test
  (:require [clojure.test :refer :all]
            [training.week2.day3.problem90 :refer [solve1 solve2 solve3]]))

(deftest solve-test
  (testing "Week2 Day3 Problem90 - 1"
    (is (solve1)))
  (testing "Week2 Day3 Problem90 - 2"
    (is (solve2)))
  (testing "Week2 Day3 Problem90 - 3"
    (is (solve3))))
