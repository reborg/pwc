(ns pwc.t-helper
  (:require [clojure.java.shell :as sh]))

(defn pwc-execute [opts]
  "Execute pwc on the given file and options after building the uberjar"
  (sh/sh "/usr/bin/env" "lein" "uberjar")
  (sh/sh "/usr/bin/env" "java" "-jar" "target/pwc.jar"))
