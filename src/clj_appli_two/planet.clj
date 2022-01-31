(ns clj-appli-two.planet
  (:require [schema.core :as s]
            [clojure.pprint :refer [pprint]]))


(s/defschema PlanetS
  {:name        s/Str
   :moons       s/Num
   :volume      s/Num
   :mass        s/Num
   :aphelion    s/Num
   :periphelion s/Num})

(defn euclidian-norm [ecc-vector] 1334e23)

(defrecord Planet [name moons volume mass aphelion periphelion eccentricity])

(declare orbital-period)

(defn orbital-period-transformation [star]
  (map #(orbital-period % (:mass star))))

(defn orbital-periods [planets star]
  (into [] (orbital-period-transformation star) planets))

(defn make-planet
  "Makes planet from fields values and eccentricity vector"
  [name moons volume mass aphelion periphelion ecc-vector]
  (->Planet name moons volume mass aphelion periphelion (euclidian-norm ecc-vector)))

(defrecord Mission [name system lauched manned?])

(def mission-defaults {:orbits 0 :evas 0})

(defn total-moons [planets]
  (transduce (map :moons) + 0 planets))

(total-moons [(->Planet "jup" 3 50 22 1445 435345 123)
              (->Planet "earth" 1 50 22 1445 435345 123)])

(defn find-planet [planets pname]
  (reduce
    (fn [_ planet]
      (when (= pname (:name planet))
        (reduced planet)))
    planets))
(find-planet [(->Planet "jup" 3 50 22 1445 435345 123)
              (->Planet "jup" 3 50 22 1445 435345 123)
              (->Planet "jup" 3 50 22 1445 435345 123)
              (->Planet "jup" 3 50 22 1445 435345 123)
              (->Planet "jup" 3 50 22 1445 435345 123)
              (->Planet "jup" 3 50 22 1445 435345 123)
              (->Planet "earth" 1 50 22 1445 435345 123)] "earth")

(defn planet? [ent]
  (instance? Planet ent))

(planet? (->Planet "shit" 2 23 23 23 23 23))
(planet? (->Mission "crap" "shit" "never" false))

(def moons-transform (comp (filter planet?) (map :moons)))

(defn total-moons2 [entities]
  (transduce moons-transform + 0 entities))

(total-moons2 [(->Mission "nada" "a" "ver" false)
               (->Mission "nada" "a" "ver" false)
               (->Planet "jup" 3 50 22 1445 435345 123)
               (->Planet "earth" 1 50 22 1445 435345 123)])

(defn make-mission
  [name system launched manned? & opts]
  (let [{:keys [cm-name lm-name orbits evas]
         :or   {orbits 0 evas 0}} opts]
    (pprint cm-name)
    nil))

(def apolo4
  (make-mission "Apolo4" "Solar" "2020-10-10T:00:00:00-00:00" true :cm-name "fred"))


(s/def earthmap :- PlanetS {:name        "earth2"
                            :moons       1
                            :volume      1.0e12
                            :mass        5.972e24
                            :aphelion    152098232
                            :periphelion 147098290})

(def earth2
  (map->Planet earthmap))

(pprint earth2)

(defn export-planet [planet]
  (select-keys planet [:name :moons]))
(export-planet earth2)
