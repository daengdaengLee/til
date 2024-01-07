(ns training.week3.day4.problem67-test
  (:require [clojure.test :refer :all]
            [training.week3.day4.problem67 :refer [solve1 solve2 solve3]]))

(deftest solve-test
  (testing "Week3 Day4 Problem67 - 1"
    (is (solve1)))
  (testing "Week3 Day4 Problem67 - 2"
    (is (solve2)))
  (testing "Week3 Day4 Problem67 - 3"
    (is (solve3))))

