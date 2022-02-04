(ns clj-appli-two.shipping.sequence
  (:require [clj-appli-two.shipping.domain :refer [ground?]]))

(defn ground-weight [products]
  (->> products
       (filter ground?)
       (pmap :weight)
       (reduce +)))
