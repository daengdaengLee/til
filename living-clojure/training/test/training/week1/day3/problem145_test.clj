(ns training.week1.day3.problem145-test
  (:require [clojure.test :refer :all]
            [training.week1.day3.problem145 :refer [solve1 solve2 solve3]]))

(deftest solve1-test
  (testing "Week1 Day3 Problem145 - 1"
    (is (solve1)))
  (testing "Week1 Day3 Problem145 - 2"
    (is (solve2)))
  (testing "Week1 Day3 Problem145 - 3"
    (is (solve3))))
