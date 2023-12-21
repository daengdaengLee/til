(ns training.week2.day2.problem66-test
  (:require [clojure.test :refer :all]
            [training.week2.day2.problem66 :refer [solve1 solve2 solve3 solve4]]))

(deftest solve-test
  (testing "Week2 Day2 Problem66 - 1"
    (is (solve1)))
  (testing "Week2 Day2 Problem66 - 2"
    (is (solve2)))
  (testing "Week2 Day2 Problem66 - 3"
    (is (solve3)))
  (testing "Week2 Day2 Problem66 - 4"
    (is (solve4))))
