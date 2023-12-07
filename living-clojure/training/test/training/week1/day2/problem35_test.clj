(ns training.week1.day2.problem35-test
  (:require [clojure.test :refer :all]
            [training.week1.day2.problem35 :refer [solve1 solve2 solve3]]))

(deftest solve1-test
  (testing "Week1 Day2 Problem35 - 1"
    (is (solve1)))
  (testing "Week1 Day2 Problem35 - 2"
    (is (solve2)))
  (testing "Week1 Day2 Problem35 - 3"
    (is (solve3))))
