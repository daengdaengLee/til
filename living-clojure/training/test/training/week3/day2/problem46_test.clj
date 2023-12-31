(ns training.week3.day2.problem46-test
  (:require [clojure.test :refer :all]
            [training.week3.day2.problem46 :refer [solve1 solve2 solve3 solve4]]))

(deftest solve-test
  (testing "Week3 Day2 Problem46 - 1"
    (is (solve1)))
  (testing "Week3 Day2 Problem46 - 2"
    (is (solve2)))
  (testing "Week3 Day2 Problem46 - 3"
    (is (solve3)))
  (testing "Week3 Day2 Problem46 - 4"
    (is (solve4))))
