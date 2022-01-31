(ns clj-appli-two.custom-col
  (:import (clojure.lang Seqable Counted Indexed ILookup)
           (java.io Writer)))

(deftype Pair [a b]
  Seqable
  (seq [_] (seq [a b]))

  Counted
  (count [_] 2)

  Indexed
  (nth [_ i]
    (case i
      0 a
      1 b
      (throw (IllegalArgumentException.))))
  (nth [this i _] (nth this i))

  ILookup
  (valAt [_ k _]
    (case k
      0 a
      1 b
      (throw (IllegalArgumentException.))))
  (valAt [this k] (.valAt this k nil)))

(defmethod print-method Pair
  [pair ^Writer w]
  (.write w "#clj-appli-two.custom-col.Pair")
  (print-method (vec (seq pair)) w))
(defmethod print-dup Pair
  [pair w]
  (print-method pair w))

(comment

  (->Pair 1 2)

  (def p (->Pair :a :b))
  (println p)
  (nth p 3)

  )


