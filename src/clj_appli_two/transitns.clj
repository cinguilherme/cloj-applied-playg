(ns clj-appli-two.transitns
  (:require [cognitect.transit :as transit])
  (:import [java.io ByteArrayInputStream ByteArrayOutputStream]))

(def out (ByteArrayOutputStream. 4096))
(def writer (transit/writer out :json-verbose))

(transit/write writer
               [
                {:name "Gui" :email "cinguilherme" :roles [:dev :eng :admin]}
                {:name "Peka" :email "emillybraga" :roles [:admin]}])

(println out)


