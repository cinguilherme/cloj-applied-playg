(ns clj-appli-two.chap8.test
  (:require [clojure.test :refer :all]))

(deftest test-range
  (testing " Testing range(endIndex)"
    (are [expected end-index]
      (= expected (range end-index))
      '(0 1 2 3 4) 5
      '() 0)))

(deftest range-exception
  (is (thrown-with-msg? ClassCastException
                        #"java.lang.String"
               (doall (range "boom")))))

(run-tests)
