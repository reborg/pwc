(ns pwc.t-helper
  (:require [clojure.java.shell :as sh]
            [iota :as iota]
            [clojure.java.io :as io]))

(defn pwc-execute [& opts]
  "Execute pwc on the given file and options after building the uberjar"
  (sh/sh "/usr/bin/env" "lein" "uberjar")
  (apply sh/sh (into ["/usr/bin/env" "java" "-jar" "target/pwc.jar"] opts)))

(defn abs-path [fname]
  "Given a resource accessible from the classpath,
  returns the absolute path of the resource"
  (.getPath (io/resource fname)))

(defn iota-seq [fname]
  "Creates a iota sequence from a classpath relative name"
  (iota/seq (abs-path fname)))
