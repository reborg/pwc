(ns pwc.core
  (:use [clojure.tools.cli :only (cli)])
  (:use [pwc.word-freq :only (wf)])
  (:require [clojure.pprint :as p])
  (:gen-class :main true))

(defn -main [& args]
  "pwc execution main entry point"
  (let [[opts args banner] (cli args
                                ["-c" "--chars" "not implemented" :flag true]
                                ["-l" "--lines" "not implemented" :flag true]
                                ["-m" "--multibte" "not implemented" :flag true]
                                ["-w" "--words" "not implemented" :flag true])]
    (if (empty? (filter #(not (= "" %)) args))
      (do 
        (println "Missing input file: pwc [-clmw] <file>. Other flags" banner)
        (System/exit 1))
      (do
        (p/pprint (wf (slurp (first args))))))))
