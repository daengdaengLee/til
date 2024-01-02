(ns training.week3.day3.problem43-test
  (:require [clojure.test :refer :all]
            [training.week3.day3.problem43 :refer [solve1 solve2 solve3]]))

(deftest solve-test
  (testing "Week3 Day3 Problem43 - 1"
    (is (solve1)))
  (testing "Week3 Day3 Problem43 - 2"
    (is (solve2)))
  (testing "Week3 Day3 Problem43 - 3"
    (is (solve3))))
