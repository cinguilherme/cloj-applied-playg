(ns clj-appli-two.chap5.jqueues
  (:import [java.util.concurrent LinkedBlockingQueue Executors]))

(def processors (.availableProcessors (Runtime/getRuntime)))

(defonce executors (Executors/newFixedThreadPool processors))

(defn submit-task [^Runnable task]
  (.submit executors task))

(defn pusher [q n]
  (loop [i 0]
    (when (< i n)
      (.put q i)
      (recur (inc i))))
  (.put q :END))

(defn popper [q]
  (loop [items []]
    (let [item (.take q)]
      (if (= item :END)
        items
        (recur (conj items item))))))

(defn flow [n]
  (let [q (LinkedBlockingQueue.)
        consumer (future (popper q))
        begin (System/currentTimeMillis)
        producer (future (pusher q n))
        received @consumer
        end (System/currentTimeMillis)]
    (println "received:" (count received) "in" (- end begin) "ms")))

(flow :END)

