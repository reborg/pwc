(ns pwc.t-core
  (:use midje.sweet)
  (:use pwc.t-helper))

(facts "Exit codes"
       (fact "exit with error on missing input file"
             (:exit (pwc-execute "")) => 1)
       (fact "exit success when mandatory arguments are obeyed"
             (:exit (pwc-execute "-l" "README.md")) => 0))

(facts "general usage facts"
       (fact "prints alert message on missing file"
             (:out (pwc-execute "")) => (contains "Usage"))
       (fact "word frequencies are not included by default"
             (:out (pwc-execute "README.md")) => "   34 342 1940 README.md\n")
       (fact "it also outputs frequencies"
             (:out (pwc-execute "-f" "README.md")) => (contains "taps"))) 

;; Lengthy test, uncomment when needed
;; (facts "big files"
;;        (fact "should not throw out of mem"
;;              (.contains (:err (pwc-execute "~/tmp/huge-file.txt")) "Exception") => falsey))
