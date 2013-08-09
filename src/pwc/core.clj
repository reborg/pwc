(ns pwc.core
  (:use [clojure.tools.cli :only (cli)])
  (:use [pwc.word-freq :only (wf)])
  (:gen-class :main true))

(defn -main [& args]
  "pwc execution main entry point"
  (let [[opts args banner] (cli args
                                ["-c" "--chars" "not implemented" :flag true]
                                ["-l" "--lines" "not implemented" :flag true]
                                ["-m" "--multibyte" "not implemented" :flag true]
                                ["-w" "--words" "not implemented" :flag true])]
    (if (empty? (remove #(= "" %) args))
      (do 
        (println "Missing input file: pwc [-clmw] <file>. Other flags" banner)
        (System/exit 1))
      (let [rs (wf (slurp (first args)))
            lines (:l rs)
            words (:w rs)
            byts  (:c rs)]
        (println (str "   " lines " " words " " byts " " (first args)))))))
