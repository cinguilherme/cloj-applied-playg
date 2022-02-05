(ns clj-appli-two.components.approvals
  (:require [com.stuartsierra.component :as component]))

(declare process-alert)
(declare process-responses)

(defrecord Approvals [approval-config alert-chan knowledge-engine response-chan]

  component/Lifecycle

  (start [component]
    (process-alert alert-chan)
    (process-responses knowledge-engine response-chan))

  (stop [component]
    component))

(defn new-approvals [approval-config alert-chan response-chan]
  (map->Approvals {:approval-config approval-config
                   :alert-chan      alert-chan
                   :response-chan   response-chan}))