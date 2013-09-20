(defproject pwc "0.2.1"
  :description "Like the venerable wc unix command, but parallel on multi-core CPUs"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.cli "0.2.2"]
                 [iota "1.1.1"]]
  :plugins [[lein-midje "3.1.1"]]
  :main pwc.core
  :uberjar-name "pwc.jar"
  :profiles {:dev {:dependencies [[midje "1.6-alpha2"]
                                  [xrepl "0.1.1"]]}})
