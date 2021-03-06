(ns clj-appli-two.chap8.check
  (:require [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clj-appli-two.recipe :as rec :refer [map->Ingredient convert ingredient+]]))

(def pos-i (gen/fmap inc gen/nat))

(def range-count-eq-n
  (prop/for-all [n gen/nat]
                (= n (count (range n)))))

(tc/quick-check 100 range-count-eq-n)

(def gen-food (gen/elements ["flour" "sugar" "butter"]))
(def gen-unit (gen/elements [:oz :lb]))

(def gen-ingredient
  (gen/fmap map->Ingredient
            (gen/hash-map :name gen-food
                          :quantity (gen/fmap inc gen/nat)
                          :unit gen-unit)))

(gen/sample gen-ingredient)

(def identity-conversion-prop
  (prop/for-all [unit gen-unit
                 quantity (gen/fmap inc gen/nat)]
                (= quantity (convert unit unit quantity))))

(tc/quick-check 10 identity-conversion-prop)

(def convertion-order-prop
  (prop/for-all [u1 gen-unit
                 u2 gen-unit
                 u3 gen-unit
                 u4 gen-unit
                 n (gen/fmap inc gen/nat)]
                (= (->> n (convert u1 u2) (convert u2 u3) (convert u3 u4))
                   (->> n (convert u1 u3) (convert u3 u2) (convert u2 u4)))))

(tc/quick-check 100 convertion-order-prop)

(def round-trip-conversion-prop
  (prop/for-all [u1 gen-unit u2 gen-unit
                 q pos-i]
                (and (= q
                        (convert u1 u2 (convert u2 u1 q))
                        (convert u2 u1 (convert u1 u2 q))))))

(tc/quick-check 100 round-trip-conversion-prop)

(defn add-and-convert [i1 i2 i3 output-unit]
  (let [{:keys [quantity unit]} (ingredient+ i1 (ingredient+ i2 i3))]
    (convert unit output-unit)))

(def associative-ingredient+-prop
  (prop/for-all [i1 gen-ingredient
                 i2 gen-ingredient
                 i3 gen-ingredient]
                (= (add-and-convert i1 i2 i3 (:unit i1))
                   (add-and-convert i3 i2 i1 (:unit i1))
                   (add-and-convert i2 i1 i3 (:unit i1)))))