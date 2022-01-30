(ns clj-appli-two.planet
  (:require [schema.core :as s]
            [clojure.pprint :refer [pprint]]))


(s/defschema PlanetS
  {:name s/Str
   :moons s/Num
   :volume s/Num
   :mass s/Num
   :aphelion s/Num
   :periphelion s/Num})

(defn euclidian-norm [ecc-vector] 1334e23)

(defrecord Planet [name moons volume mass aphelion periphelion eccentricity])

(defn make-planet
  "Makes planet from fields values and eccentricity vector"
  [name moons volume mass aphelion periphelion ecc-vector]
  (->Planet name moons volume mass aphelion periphelion (euclidian-norm ecc-vector)))

(defrecord Mission [name system lauched manned?])

(def mission-defaults {:orbits 0 :evas 0})

(defn make-mission
  [name system launched manned? & opts]
  (let [{:keys [cm-name lm-name orbits evas]
         :or {orbits 0 evas 0}} opts]
    (pprint cm-name)
    nil))

(def apolo4
  (make-mission "Apolo4" "Solar" "2020-10-10T:00:00:00-00:00" true :cm-name "fred"))


(s/def earthmap :- PlanetS {:name "earth2"
                            :moons 1
                            :volume 1.0e12
                            :mass 5.972e24
                            :aphelion 152098232
                            :periphelion 147098290})

(def earth2
  (map->Planet earthmap))

(pprint earth2)