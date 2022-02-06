(ns clj-appli-two.money-transit
  (:require [cognitect.transit :as transit]
            [clj-appli-two.money])
  (:import [clj_appli_two.money Currency]))

#_(def write-handlers
  {
   Currency

   (reify Writehandler
     (tag [_ __] "currency")
     (rep [_ c] [(:divisor c) (:sym c) (:desc c)])
     )

   })
