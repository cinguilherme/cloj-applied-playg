(defproject clj-appli-two "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [prismatic/schema "1.2.0"]
                 [medley "1.3.0"]
                 [org.clojure/test.check "1.1.1"]
                 [org.clojure/core.async "1.5.648"]
                 [com.stuartsierra/component "1.0.0"]
                 [levand/immuconf "0.1.0"]
                 [org.clojure/data.json "2.4.0"]
                 [cheshire "5.10.2"]
                 [com.cognitect/transit-clj "1.0.329"]]

  :test-selectors {:string :string}

  :repl-options {:init-ns clj-appli-two.core})
