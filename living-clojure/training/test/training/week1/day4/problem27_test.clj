(ns training.week1.day4.problem27-test
  (:require [clojure.test :refer :all]
            [training.week1.day4.problem27 :refer [solve1 solve2 solve3 solve4 solve5]]))

(deftest solve-test
  (testing "Week1 Day4 Problem27 - 1"
    (is (solve1)))
  (testing "Week1 Day4 Problem27 - 2"
    (is (solve2)))
  (testing "Week1 Day4 Problem27 - 3"
    (is (solve3)))
  (testing "Week1 Day4 Problem27 - 4"
    (is (solve4)))
  (testing "Week1 Day4 Problem27 - 5"
    (is (solve5))))
