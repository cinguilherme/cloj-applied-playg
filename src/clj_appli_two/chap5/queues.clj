(ns clj-appli-two.chap5.queues
  (:import (clojure.lang PersistentQueue)))

(defn queue []
  (ref PersistentQueue/EMPTY))

(defn enq [q item]
  (dosync (alter q conj item)))

(defn deq [q]
  (dosync
    (let [item (peek @q)]
      (alter q pop)
      item)))


