(ns training.week1.day5.problem31-test
  (:require [clojure.test :refer :all]
            [training.week1.day5.problem31 :refer [solve1 solve2 solve3]]))

(deftest solve1-test
  (testing "Week1 Day5 Problem31 - 1"
    (is (solve1)))
  (testing "Week1 Day5 Problem31 - 2"
    (is (solve2)))
  (testing "Week1 Day5 Problem31 - 3"
    (is (solve3))))
