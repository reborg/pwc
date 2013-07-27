(defproject pwc "0.0.1-SNAPSHOT"
  :description "The venerable wc unix command, parallel version."
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :plugins [[lein-midje "3.1.1"]]
  :profiles {:dev {:dependencies [[midje "1.6-alpha2"]]}})
