(ns training.week2.day2.problem83-test
  (:require [clojure.test :refer :all]
            [training.week2.day2.problem83 :refer [solve1 solve2 solve3 solve4 solve5 solve6]]))

(deftest solve-test
  (testing "Week2 Day2 Problem83 - 1"
    (is (solve1)))
  (testing "Week2 Day2 Problem83 - 2"
    (is (solve2)))
  (testing "Week2 Day2 Problem83 - 3"
    (is (solve3)))
  (testing "Week2 Day2 Problem83 - 4"
    (is (solve4)))
  (testing "Week2 Day2 Problem83 - 5"
    (is (solve5)))
  (testing "Week2 Day2 Problem83 - 6"
    (is (solve6))))
