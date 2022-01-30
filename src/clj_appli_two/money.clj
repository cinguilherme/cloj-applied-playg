(ns clj-appli-two.money)

(declare validade-same-currency)

(defrecord Currency [div sym desc])

(defrecord Money [amount ^Currency currency]

  java.lang.Comparable

  (compareTo [m1 m2]
    (validade-same-currency m1 m2)
    (compare (:amount m1) (:amount m2))))

(def currencies
  {:usd (->Currency 100 "USD" "US Dollars")
   :eur (->Currency 100 "EUR" "Euro")})

(defn- validade-same-currency [m1 m2]
  (or (= (:currency m1) (:currency m2))
      (throw (ex-info "Currency does not match" {:m1 m1 :m2 m2}))))

(defn $=
  ([m1] true)
  ([m1 m2] (zero? (.compareTo m1 m2)))
  ([m1 m2 & monies]
   (every? zero? (map #(.compareTo m1 %) (conj monies m2)))))

(defn +$
  ([m1] m1)
  ([m1 m2]
   (validade-same-currency m1 m2)
   (->Money (+ (:amount m1) (:amount m2)) (:currency m1)))
  ([m1 m2 & monies]
   (validade-same-currency m1 m2)
   (reduce +$ m1 (conj monies m2))))

(defn *$ [m n] (->Money (* n (:amount m))  (:currency m)))

(def zero-dollar (->Money 0 :usd))

(defn make-money
  ([] (make-money 0))
  ([amount] (make-money amount (:usd currencies)))
  ([amount currency] (->Money amount currency)))

(make-money 100)