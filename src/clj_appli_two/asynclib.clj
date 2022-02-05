(ns clj-appli-two.asynclib
  (:require [clojure.core.async :refer [chan >!! <!! >! <! go go-loop]]))

(def c (chan 1))

(defn go-print [c]
  (go
    (loop []
      (when-some [val (<! c)]
        (println "recieved message " val)
        (recur)))))

(go-print c)

(go (map #(>! c "hello there boys!!") (range 10)))
(go (>! c "hello there boys!!"))

(let [future-comp (promise)]
  (println (realized? future-comp))
  (deliver future-comp 10)
  (println @future-comp))