(ns pwc.t-word-freq
  (:use midje.sweet)
  (:require [clojure.java.io :as io])
  (:use [pwc.word-freq]))

(facts "hash monoid"
       (fact "return the seed with no parameters"
             (hash-monoid) => {})
       (fact "perform combination of two maps"
             (hash-monoid {"a" 1} {"b" 2}) => {"a" 1 "b" 2}))

(facts "sentence tokenization"
       (fact "empty text is empty list"
             (into [] (tokenize "")) => [])
       (fact "single token text"
             (into [] (tokenize "a")) => ["a"])
       (fact "multiple tokens text"
             (into [] (tokenize "a b cc")) => ["a" "b" "cc"])
       (facts "special characters"
              (fact "newlines"
                    (into [] (tokenize "a \n b")) => ["a" "b"])
              (fact "tabs"
                    (into [] (tokenize "a \t b")) => ["a" "b"])
              (fact "null char"
                    (into [] (tokenize "a \0 b")) => ["a" "b"])
              (fact "everything"
                    (into [] (tokenize "n1\0\tbc \ta")) => ["n1" "bc" "a"]))
       (fact "optionally process words"
             (into [] (tokenize "A Bb" #(.toLowerCase %)))  => ["a" "bb"]))

(facts "altering the counter maps"
       (fact "new map created if no element"
             (inc-or-add {} "a") => {"a" 1}
             (inc-or-add {"b" 1} "a") => (contains {"a" 1}))
       (fact "already present element is incremented"
             (inc-or-add {"a" 1} "a") => {"a" 2})
       (fact "it is perfectly happy to use the monoid"
             (inc-or-add (hash-monoid) "a") => {"a" 1}))


(facts "counting words"
       (fact "a single word string"
             (first (wf "a")) => ["a" 1])
       (fact "frequencies are ordered DESC by default"
             (first (wf "a a a b b c")) => ["a" 3])
       (fact "some more complicated text"
             werCase %)
             (let [divina (slurp (io/resource "divina-commedia.txt"))]
               (first (wf divina)) => ["e" 4862]))

(facts "perfomances"
       (let [war (slurp (io/resource "war-and-peace.txt"))]
         (fact "sequential war and peace"
               (first (time (sequential-wf war))) => ["the" 34721])
         (fact "parallel war and peace"
               (first (time (wf war))) => ["the" 34721])))
