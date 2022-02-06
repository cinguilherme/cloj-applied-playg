(ns clj-appli-two.chap10.date-fmt
  (:require [cheshire.core :refer :all]
            [cheshire.generate :refer [add-encoder]]))

(def ^:private date-format
  (proxy [ThreadLocal] []
    (initialValue []
      (doto (java.text.SimpleDateFormat. "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")))))

(defn- format-inst [d]
  (str "#inst"  (.format (.get date-format) d)))

(defn- date-part [d]
  (second (re-matches #"#inst (.*)" d)))

(add-encoder java.util.Date
             (fn [d generator]
               (.writeString generator (format-inst d))))

(defn read-date [k v]
  (if (= :date k)
    (.parse (.get date-format) (date-part v))
    v))

(defn write-date [k v]
  (if (= :date k)
    (str "#inst" (.format (.get date-format v)))
    v))

(generate-string {:date (java.util.Date.)})
