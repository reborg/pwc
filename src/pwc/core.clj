(ns pwc.core
  (:use [clojure.tools.cli :only (cli)])
  (:gen-class :main true))

(defn run
  "Print out the options and the arguments"
  [opts args]
  (println (str "Options:\n" opts "\n\n"))
  (println (str "Arguments:\n" args "\n\n")))

(defn -main [& args]
  "pwc execution main entry point"
  (let [[opts args banner] (cli args
                                ["-h" "--help" "Show help" :flag true :default false]
                                ["-d" "--delay" "Delay between messages (seconds)" :default 2]
                                ["-f" "--from" "REQUIRED: From address"]
                                ["-b" "--bcc" "OPTIONAL: BCC address"]
                                ["-t" "--test" "Test mode does not send" :flag true :default false])]
    (when (:help opts)
      (println banner)
      (System/exit 0))
    (if
      (and ;;required stuff
        (:from opts)
        true)
      (do
        (println "")
        (run opts args))
      (do 
        (println banner)
        (System/exit 1)))))
