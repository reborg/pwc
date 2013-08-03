(ns pwc.t-cmd-line
  (:use midje.sweet)
  (:use pwc.t-helper)
  (:use [pwc.cmd-line]))

(facts "parsing of input file"
       (fact "exit with error on missing input"
             (:exit (pwc-execute "")) => 1))
