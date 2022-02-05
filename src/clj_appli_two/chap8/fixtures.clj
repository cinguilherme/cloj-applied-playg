(ns clj-appli-two.chap8.fixtures
  (:require [clojure.test :refer :all]))

(def ^:dynamic *conn*)

(declare create-db-conn)
(declare load-test-data)
(declare destroy-test-data)
(declare close-db-conn)

(defn db-fixture [test-function]
  (binding [*conn* (create-db-conn)]
    (load-test-data *conn*)
    (test-function)
    (destroy-test-data *conn*)
    (close-db-conn *conn*)))

(use-fixtures :each db-fixture)

(declare add-user)
(declare check-user)
(deftest test-db-add-user
  (add-user *conn* "user")
  (check-user *conn* "user"))
