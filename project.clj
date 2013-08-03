(defproject pwc "0.0.1"
  :description "The venerable wc unix command, parallel version."
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.cli "0.2.2"]]
  :plugins [[lein-midje "3.1.1"]]
  :main pwc.core
  :uberjar-name "pwc.jar"
  :profiles {:dev {:dependencies [[midje "1.6-alpha2"]
                                  [xrepl "0.1.1"]]}})
