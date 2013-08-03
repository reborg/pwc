(ns pwc.t-cmd-line
  (:use midje.sweet)
  (:use pwc.t-helper)
  (:use [pwc.cmd-line]))

(facts "Exit codes"
       (fact "exit with error on missing input file"
             (:exit (pwc-execute "")) => 1)
       (fact "exit success when mandatory arguments are obeyed"
             (:exit (pwc-execute "-f" "yeah")) => 0))
