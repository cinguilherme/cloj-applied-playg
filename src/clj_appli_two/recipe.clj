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
(declare cost)
(declare cost-of)

(extend-protocol Cost
  Recipe
  (cost [recipe store]
    (reduce +$ zero-dollar
            (map #(cost % store) (:ingredients store))))

  Ingredient
  (cost [ingredient store]
    (cost-of store ingredient)))


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

