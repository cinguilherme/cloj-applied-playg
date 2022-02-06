(ns clj-appli-two.chap10.jsonns
  (:require [clojure.data.json :as json]
            [cheshire.core :refer :all]
            [cheshire.generate :refer [add-encoder]]
            [clj-appli-two.chap10.date-fmt :refer :all]))

(add-encoder java.util.Date
             (fn [d generator]
               (.writeString generator (format-inst d))))

(json/read-str "{\"name\": \"fred\" , \"age\": 35 }")
(json/write-str [{:name "chris"} {:name "Mina"}])

(parse-string "{\"name\": \"fred\" , \"age\": 35 }")
(parse-string "{\"name\": \"fred\" , \"age\": 35 }" true)

(generate-string [{:name "Mina"} {:name "Tina"}])

(json/write-str {:keyword "string"
                 :date (java.util.Date.)
                 :value-fn write-date} )