(ns clj-appli-two.comp-system.system
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async :refer [chan]]
            [clj-appli-two.components.comp-feed :as feed]
            [clj-appli-two.components.approvals :as c.approvals]
            [clj-appli-two.components.knowledge-engine :as ke]
            [immuconf.config :as conf]))

(def config (conf/load "resources/config.edn"
                       "user.edn"))

(conf/get config :rule-set)

(defn system [{:keys [twitter facebook knowledge approvals] :as config}]
  (let [twitter-chan (chan 100)
        twitter-response-chan (chan 10)
        facebook-chan (chan 100)
        facebook-response-chan (chan 10)
        alert-chan (chan 100)
        response-chan (chan 100)
        feed-chan (async/merge [twitter-chan facebook-chan])
        response-pub (async/pub response-chan :feed)]
    (async/sub response-pub :twitter twitter-response-chan)
    (async/sub response-pub :facebook facebook-response-chan)

    (component/system-map
      :twitter (feed/new-feed twitter twitter-chan twitter-response-chan)
      :facebook (feed/new-feed facebook facebook-chan facebook-response-chan)
      :knowledge-engine (ke/new-knowledge-engine knowledge feed-chan alert-chan)
      :approvals (component/using
                   (c.approvals/new-approvals approvals alert-chan response-chan)
                   [:knowledge-engine]))))
