(ns pwc.t-tokenizer
  (:use midje.sweet)
  (:require [pwc.tokenizer :as t]
            [criterium.core :as bench]))

(def sentence " ... I'm some text here! Again... and again")
(def expected ["I" "m" "some" "text" "here" "Again" "and" "again"])

(facts "clojury simple words splitting"
       (fact "split by single spaces"
             (t/tokenize "some text here") => ["some" "text" "here"])
       (fact "no problem starting from a space"
             (t/tokenize " some text here") => ["some" "text" "here"]))

(facts "damn fast string splitting with java"
       (fact "split by single spaces"
             (t/jtokenize "some text here") => ["some" "text" "here"])
       (fact "handle a lot of punctiation cases"
             (t/jtokenize sentence) => expected))

;;(fact "in search for raw performances"
;;      (let [my (first (:mean (bench/quick-benchmark (t/tokenize sentence) {})))
;;            regxp (first (:mean (bench/quick-benchmark (re-seq #"\w+" sentence) {})))
;;            java (first (:mean (bench/quick-benchmark (pwc.Tokenizer/tokenize sentence) {})))
;;            dbg (println "mine: " my "regexp" regxp "java" java)]
;;        (< my regxp) => falsey
;;        (< regxp java) => falsey
;;        (< java regxp) => truthy))
