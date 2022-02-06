(ns clj-appli-two.chap10.ends
  (:require [clojure.edn :as edn]
            [clj-appli-two.chap10.cards :as card]))

(def users (atom []))

(defn init-users! []
  (reset! users
          (binding [*data-readers* {'my/card' #'card/card-reader'}]
            (edn/read-string "#my/card \"2c\""))))

(init-users!)
