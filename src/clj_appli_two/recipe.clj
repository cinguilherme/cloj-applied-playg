(ns clj-appli-two.recipe
  (:require [clojure.pprint :refer [pprint]]
            [schema.core :as s]
            [clj-appli-two.core :refer [Cost]]
            [clj-appli-two.money :refer [+$ zero-dollar]]))

(s/defrecord Person [name :- s/Str
                     last-name :- s/Str])

(s/defrecord Ingredient [name :- s/Str
                         quantity :- s/Int
                         unity :- s/Keyword])

(defmulti convert (fn [unit1 unit2 quantity] [unit1 unit2]))
(defmethod convert [:lb :oz] [_ _ lb] (* lb 16))
(defmethod convert [:oz :lb] [_ _ oz] (/ oz 16))
(defmethod convert :default [u1 u2 q]
  (if (= u1 u2)
    q
    (assert false (str "Unkown unit conversion from " u1 " to " u2))))

(convert :lb :oz 5)
(convert :oz :lb 10)
;(convert :kg :lb 10)

(def ing (->Ingredient "sugar" 2 :oz))
(def ing2 (->Ingredient "sugar" 5 :lb))

(defn ingredient+ [{q1 :quantity u1 :unit :as i1} {q2 :quantity u2 :unit}]
  (assoc i1 :quantity (+ q1 (convert u2 u1 q2))))

(ingredient+ ing ing2)

(s/defschema RecipeSchema
  {:name        s/Str
   :author      Person
   :description s/Str
   :ingredients [Ingredient]
   :steps       [s/Str]
   :servings    s/Int})

(s/defrecord Recipe [name :- s/Str
                     author :- Person
                     description :- s/Str
                     ingredients :- [Ingredient]
                     steps :- [s/Str]
                     servings :- s/Int])
(defrecord Store [name tax-rate])

(declare cost)
(declare cost-of)
(declare tax-rate)

(extend-protocol Cost
  Store
  (cost [entity store]
    20)

  Recipe
  (cost [recipe store]
    (reduce +$ zero-dollar
            (map #(cost % store) (:ingredients store))))

  Ingredient
  (cost [ingredient store]
    (cost-of store ingredient)))

(defprotocol TaxedCost
  (taxed-cost [entity store]))

(extend-protocol TaxedCost
  Object
  (taxed-cost [entity store]
    (if (satisfies? Cost entity)
      (do (extend-protocol TaxedCost
            (class entity)
            (taxed-cost [entity store]
              (* (cost entity store) (+ 1 (tax-rate store)))))
          (taxed-cost entity store))
      (assert false (str "Unhandled entity:" entity)))))


(def people
  {"p1" (->Person "Alex" "Miller")})

(def spagetti-taco
  (map->Recipe
    {:name        "Spagetti tacos"
     :author      (->Person "Alex" "Miller")
     :description "Its Spagetti in a taco"
     :ingredients [(->Ingredient "Spagetti" 1 :lb)
                   (->Ingredient "Spagetti sauce" 16 :oz)
                   (->Ingredient "taco shell" 12 :shell)]
     :steps       ["Cook spagetti" "heat spagetti sauce" "mix sauce with spagetti" "put spagetti in taco shells"]
     :servings    4}))
(pprint spagetti-taco)

(def recipes
  {"r1" (->Recipe
          "Toast"
          "p1"
          "Crispy
          Bread"
          ["Sliced
         Bread"]
          ["Toast bread in toaster"]
          1)})

(pprint recipes)

(def toast
  (->Recipe
    "Toast"
    (->Person "Alex" "Miller")
    "Cripy Bread"
    ["sliced bread"]
    ["toast bread in toaster"]
    1))

(pprint toast)

(s/defn add-ingredients
  [recipe :- Recipe & ingredients :- [Ingredient]]
  (update-in recipe [:ingredients] into ingredients))

(s/with-fn-validation
  (add-ingredients spagetti-taco
                   (->Ingredient "chease" 1 :lb)
                   (->Ingredient "peper" 1 :g)))

