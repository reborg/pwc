(ns pwc.t-word-count
  (:use midje.sweet)
  (:require [clojure.java.io :as io])
  (:use [pwc.word-count]))

(facts "hash monoid"
       (fact "return the seed with no parameters"
             (hash-monoid) => {})
       (fact "perform combination of two maps"
             (hash-monoid {"a" 1} {"b" 2}) => {"a" 1 "b" 2}))

(facts "sentence tokenization"
       (fact "empty text is empty list"
             (tokenize "") => [])
       (fact "single token text"
             (tokenize "a") => ["a"])
       (fact "multiple tokens text"
             (tokenize "a b cc") => ["a" "b" "cc"])
       (facts "special characters"
              (fact "newlines"
                    (tokenize "a \n b") => ["a" "b"])
              (fact "tabs"
                    (tokenize "a \t b") => ["a" "b"])
              (fact "null char"
                    (tokenize "a \0 b") => ["a" "b"])
              (fact "everything"
                    (tokenize "n1\0\tbc \ta") => ["n1" "bc" "a"])))

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
             (first (wc "a")) => ["a" 1])
       (fact "frequencies are ordered DESC by default"
             (first (wc "a a a b b c")) => ["a" 3])
       (fact "some more complicated text"
             (let [divina (slurp (io/resource "divina-commedia.txt"))]
               (first (wc divina)) => ["e" 4476]))
       (fact "some more complicated text"
             (let [war (slurp (io/resource "war-and-peace.txt"))]
               (first (time (wc war))) => ["the" 31950])))
