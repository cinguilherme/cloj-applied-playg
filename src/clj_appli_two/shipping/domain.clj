(ns clj-appli-two.shipping.domain)

(defn ground? [m]
  (= :ground (:class m)))
