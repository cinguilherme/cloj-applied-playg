(ns clj-appli-two.util.stringx-test
  (:require [clojure.test :refer :all])
  (:require [clj-appli-two.util.stringx :refer :all]))

(deftest ^:string str-defaults
  (is (clojure.string/starts-with? "amalia" "ama")))
