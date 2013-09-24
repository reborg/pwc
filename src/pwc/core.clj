(ns pwc.core
  (:use [clojure.tools.cli :only (cli)])
  (:require [pwc.word-freq :as wf]
            [pwc.word-count :as wc]
            [iota :as iota])
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
      (if (:frequencies opts) 
        (let [rs (wf/wf (iota/seq (first args)))]
          (println (str "   " (:l rs) " " (:w rs) " " (:c rs) " " (first args) "\n" (:f rs))))
        (let [rs (wc/wc (iota/seq (first args)))]
          (println (str "   " (first rs) " " (nth rs 1) " " (last rs) " " (first args))))))))
