(ns pwc.core
  (:use [clojure.tools.cli :only (cli)])
  (:use [pwc.word-freq :only (wf)])
  (:require [iota :as iota])
  (:gen-class :main true))

(def options [["-f" "--frequencies" "output frequencies of words" :flag true]
              ["-c" "--chars" "not implemented" :flag true]
              ["-t" "--test" "empty main just to measure jvm warmup" :flag true]
              ["-l" "--lines" "not implemented" :flag true]
              ["-m" "--multibyte" "not implemented" :flag true]
              ["-w" "--words" "not implemented" :flag true]])

(defn empty-args? [args]
  (empty? (remove #(= "" %) args)))

(defn print-msg-and-exit [msg]
  (do 
    (println msg)
    (System/exit 1)))

(defn -main [& args]
  "pwc execution main entry point"
  (let [[opts args banner] (apply cli args options)]
    (if (:test opts)
      (print-msg-and-exit "Empty execution to mesure JVM startup time"))
    (if (empty-args? args)
      (print-msg-and-exit (str "Missing input file: pwc [-clmw] <file>. Other flags" banner))
      (let [rs (wf (iota/seq (first args)) (:frequencies opts))
            lines (:l rs)
            words (:w rs)
            byts  (:c rs)]
        (if (:frequencies opts) 
          (println (str "   " lines " " words " " byts " " (first args) "\n" (:f rs)))
          (println (str "   " lines " " words " " byts " " (first args))))))))
