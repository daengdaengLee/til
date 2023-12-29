(ns training.week3.day1.problem96-test
  (:require [clojure.test :refer :all]
            [training.week3.day1.problem96 :refer [solve1 solve2 solve3 solve4 solve5 solve6]]))

(deftest solve-test
  (testing "Week3 Day1 Problem96 - 1"
    (is (solve1)))
  (testing "Week3 Day1 Problem96 - 2"
    (is (solve2)))
  (testing "Week3 Day1 Problem96 - 3"
    (is (solve3)))
  (testing "Week3 Day1 Problem96 - 4"
    (is (solve4)))
  (testing "Week3 Day1 Problem96 - 5"
    (is (solve5)))
  (testing "Week3 Day1 Problem96 - 6"
    (is (solve6))))
