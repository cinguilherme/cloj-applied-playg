(ns clj-appli-two.components.feed
  (:require [clojure.core.async :as async]))

(defn make-feed-processor
  "create a feed processor on the given input channel"
  [input-channel])

(defn make-feed-processor
  "create a feed processor on the given input channel"
  [input-channel]
  (let [c (async/chan 100)]))

(defn input-chan [feed-processor])
