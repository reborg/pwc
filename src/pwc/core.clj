(ns pwc.core
  (:gen-class :main true))

(defn -main [& args]
  "pwc execution main entry point"
  (if args
    (println (str "You passed in this value: " args))
    (do (println "Usage: cmdline VALUE") (System/exit 1))))
