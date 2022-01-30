(ns clj-appli-two.prots-play)

(defrecord Per [name age acc])
(defrecord Cli [name acc other])

(defprotocol NameWithAge
  (name-age [el]))

(extend-protocol NameWithAge
  Per
  (name-age [el]
    (clojure.pprint/pprint (str (:name el) " age:" (:age el)))))

(def p1 (->Per "Gui" 35 11))
(def p2 {:name "todd" :age 11})
(name-age p1)
;;(name-age p2)

(name-age-m p2)
