(ns training.week1.day2.problem36-test
  (:require [clojure.test :refer :all]
            [training.week1.day2.problem36 :refer [solve1 solve2 solve3]]))

(deftest solve-test
  (testing "Week1 Day2 Problem36 - 1"
    (is (solve1)))
  (testing "Week1 Day2 Problem36 - 2"
    (is (solve2)))
  (testing "Week1 Day2 Problem36 - 3"
    (is (solve3))))
