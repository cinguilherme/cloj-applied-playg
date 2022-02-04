(ns clj-appli-two.shipping.reducers
  (:require [clj-appli-two.shipping.domain :refer [ground?]]
            [clojure.core.reducers :as r]))

(defn ground-weight [products]
  (->> products
       (r/filter ground?)
       (r/map :weight)
       (r/fold +)))

