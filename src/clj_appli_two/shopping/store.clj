(ns clj-appli-two.shopping.store)

(def inventory (atom {}))

(defn no-negative-values? [m]
  (not-any? (neg? (vals m))))

(defn in-stock [item]
  (let [cnt (item @inventory)]
    (pos? cnt)))

(defn init [items]
  (set-validator! inventory no-negative-values?)
  (swap! inventory items))

(defn grab [item]
  (swap! inventory update-in [item] dec))

(defn store [item]
  (swap! inventory update-in [item] inc))

(declare sold-items)

(defn restock-order
  [k re ov nv]
  (doseq [item (for [kw (keys ov)
                     :when (not= (kw ov) (kw nv))] kw)]
    (swap! sold-items update-in [item] (fnil inc 0))
    (println "need restock" item)))
