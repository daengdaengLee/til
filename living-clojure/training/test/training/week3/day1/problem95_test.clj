(ns training.week3.day1.problem95-test
  (:require [clojure.test :refer :all]
            [training.week3.day1.problem95 :refer [solve1 solve2 solve3 solve4 solve5 solve6 solve7]]))

(deftest solve-test
  (testing "Week3 Day1 Problem95 - 1"
    (is (solve1)))
  (testing "Week3 Day1 Problem95 - 2"
    (is (solve2)))
  (testing "Week3 Day1 Problem95 - 3"
    (is (solve3)))
  (testing "Week3 Day1 Problem95 - 4"
    (is (solve4)))
  (testing "Week3 Day1 Problem95 - 5"
    (is (solve5)))
  (testing "Week3 Day1 Problem95 - 6"
    (is (solve6)))
  (testing "Week3 Day1 Problem95 - 7"
    (is (solve7))))
