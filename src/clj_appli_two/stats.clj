(ns clj-appli-two.stats)

(declare remote-send)

(def page-stats (agent 0))

(add-watch
  page-stats
  :pageview
  (fn [key agent old new]
    (when (zero? (mod new 10))
      (remote-send key new))))

(defn inc-stat [stat]
  (send-off stat inc))
