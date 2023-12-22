(ns training.week2.day3.problem107-test
  (:require [clojure.test :refer :all]
            [training.week2.day3.problem107 :refer [solve1 solve2 solve3]]))

(deftest solve-test
  (testing "Week2 Day3 Problem107 - 1"
    (is (solve1)))
  (testing "Week2 Day3 Problem107 - 2"
    (is (solve2)))
  (testing "Week2 Day3 Problem107 - 3"
    (is (solve3))))
