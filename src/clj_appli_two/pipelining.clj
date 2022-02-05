(ns clj-appli-two.pipelining
  (:require [clojure.core.async :as async :refer [chan >! <! go >!! <!! pipeline]]))

;parse message into set of words
(def parse-words (map #(set (clojure.string/split % #"\s"))))

;filter messages that contains words of interest
(def interesting (filter #(contains? % "Clojure")))

(defn match [search-words message-words]
  (count (clojure.set/intersection search-words message-words)))

(def happy (partial match #{"happy" "awesome" "rocks" "amazing"}))
(def sad (partial match #{"sad" "bug" "crash"}))

(def score (map #(hash-map :words %1
                           :happy (happy %1)
                           :sad (sad %1))))

(defn sentiment-stage [in out]
  (let [xf (comp parse-words interesting score)]
    (async/pipeline 4 out xf in)))

(defn score-stage [intermediate out]
  (async/pipeline 1 out score intermediate))

(defn interesting-stage [in intermediate]
  (let [xf (comp parse-words interesting)]
    (async/pipeline 4 intermediate xf in)))

(defn assemble-stages [in out]
  (let [intermediate (async/chan 100)]
    (interesting-stage in intermediate)
    (score-stage intermediate out)))

(comment
  (def in (chan 10))
  (def out (chan 10))
  (sentiment-stage in out)

  (go
    (>! in "hey man, this is really sad but I have to tell you..")
    (>! in "hey man Clojure")
    (>! in "hey man, this is really sad but I have to tell you..")
    (>! in "hey man, this is really sad but I have to tell you.."))

  (go
    (println (<! out)))
  )