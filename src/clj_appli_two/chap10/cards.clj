(ns clj-appli-two.chap10.cards)

(defrecord Card [rank suit])

(def ranks "23456789TJQKA")
(def suits "hdcs")

(defn- check [val vals]
  (if (some #{val} (set vals))
    val
    (throw (IllegalArgumentException.
             (format "Invalid value %s, expected %s" val vals)))))

(defn card-reader [card]
  (let [[rank suit] card]
    (->Card (check rank ranks) (check suit suits))))

(card-reader "2c")

(defn card-str [card]
  (str  "#my/card \"" (:rank card) (:suit card) "\""))
(card-str (card-reader "2c"))

(defmethod print-method clj-appli-two.chap10.cards.Card [card ^java.io.Writer w]
  (.write w (card-str card)))

(defmethod print-dup clj-appli-two.chap10.cards.Card [card w]
  (print-method card w))