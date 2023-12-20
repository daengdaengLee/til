(ns training.week2.day1.problem48-test
  (:require [clojure.test :refer :all]
            [training.week2.day1.problem48 :refer [solve1 solve2]]))

(deftest solve1-test
  (testing "Week2 Day1 Problem48 - 1"
    (is (solve1)))
  (testing "Week2 Day1 Problem48 - 2"
    (is (solve2))))
