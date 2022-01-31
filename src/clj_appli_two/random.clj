(ns clj-appli-two.random
  (:require [medley.core :refer [map-keys map-vals]]))

(defn compare-author [a1 a2]
  (letfn [(project-author [author]
            ((juxt :lname :fname) author))]
    (compare (project-author a1) (project-author a2))))

((juxt :lname :fname) {:fname "gui" :lname "cintra"})

(sorted-set-by compare-author
               {:fname "flip" :lname "cintra"}
               {:fname "gui" :lname "cintra"}
               {:fname "amalia" :lname "cintra"})


(def json {"name" "gui"
           "lname" "cintra"})
(defn keywordize-entity [entity]
  (map-keys keyword entity))
(keywordize-entity json)

(declare computed-calories)

(defn- update-calories [recipe]
  (assoc recipe :calories (computed-calories recipe)))

(defn include-calories [recipe-index]
  (map-vals update-calories recipe-index))
