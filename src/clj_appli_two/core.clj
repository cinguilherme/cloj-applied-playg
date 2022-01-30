(ns clj-appli-two.core)

(defprotocol Cost
  (cost [entity store]))
