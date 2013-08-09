(ns pwc.t-cmd-line
  (:use midje.sweet)
  (:use pwc.t-helper)
  (:use [pwc.cmd-line]))

(facts "Exit codes"
       (fact "exit with error on missing input file"
             (:exit (pwc-execute "")) => 1)
       (fact "exit success when mandatory arguments are obeyed"
             (:exit (pwc-execute "-l" "README.md")) => 0))

(facts "general usage facts"
       (fact "prints alert message on missing file"
             (:out (pwc-execute "")) => (contains "Usage"))
       (fact "word frequencies are not included by default"
             (:out (pwc-execute "test/divina-commedia.txt")) => "   14723 105526 560110 test/divina-commedia.txt\n"))
