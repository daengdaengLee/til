(ns training.week2.day5.problem97-test
  (:require [clojure.test :refer :all]
            [training.week2.day5.problem97 :refer [solve1 solve2 solve3]]))

(deftest solve-test
  (testing "Week2 Day5 Problem97 - 1"
    (is (solve1)))
  (testing "Week2 Day5 Problem97 - 2"
    (is (solve2)))
  (testing "Week2 Day5 Problem97 - 3"
    (is (solve3))))
