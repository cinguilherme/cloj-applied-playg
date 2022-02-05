(ns clj-appli-two.components.knowledge-engine
  (:require [com.stuartsierra.component :as component]))

(declare watch-feeds)

(defrecord KnowledgeEngine [ke-config feed-chan alert-chan rules]
  component/Lifecycle

  (start [component]
    (watch-feeds feed-chan alert-chan)
    component)

  (stop [component]
    component))

(defn new-knowledge-engine
  [ke-config feed-chan alert-chan]
  (->KnowledgeEngine ke-config feed-chan alert-chan (atom (:rule-set ke-config))))