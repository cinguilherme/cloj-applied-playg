(ns clj-appli-two.shopping.single
  (:require [clj-appli-two.shopping.store :as store]))

(defn shop-for-item [cart item]
  (if (store/grab item)
    (conj cart item)
    cart))

(defn go-shopping [shopping-list]
  (reduce shop-for-item [] shopping-list))
