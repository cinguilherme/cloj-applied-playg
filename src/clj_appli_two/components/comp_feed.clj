(ns clj-appli-two.components.comp-feed
  (:require [com.stuartsierra.component :as component]))

(declare process-messages)
(declare handle-responses)

(defrecord Feed [auth status msg-chan response-chan]
  component/Lifecycle

  (start [component]
    (reset! (:status component) :running)
    (process-messages status msg-chan)
    (handle-responses status response-chan)
    component)

  (stop [component]
    (reset! (:status component) :stopped)))

(defn new-feed [auth msg-chan response-chan]
  (->Feed auth (atom :init) msg-chan response-chan))
