(ns pwc.t-word-freq
  (:use midje.sweet)
  (:require [clojure.java.io :as io])
  (:use [pwc.word-freq]))

(facts "combining function construction"
       (fact "return the seed with no parameters"
             (combine-f) => seed)
       (fact "summing up result counters from different threads"
             (combine-f {:l 1 :w 1 :c 1 :f {}} {:l 1 :w 1 :c 1 :f {}}) => {:l 2 :w 2 :c 2 :f {}})
       (fact "frequencies are merged with +"
             (combine-f {:f {"a" 1 "b" 3}} {:f {"b" 5}}) => {:f {"a" 1 "b" 8}}))

(facts "text is split into lines"
       (fact "empty text is empty list"
             (into [] (tokenize "")) => [])
       (fact "single token text"
             (into [] (tokenize "a")) => ["a"])
       (fact "multiple tokens text"
             (into [] (tokenize "a b cc\n 123")) => ["a b cc" " 123"])
       (facts "special characters"
              (fact "newlines"
                    (into [] (tokenize "a \n b")) => ["a " " b"])
              (fact "tabs"
                    (into [] (tokenize "a \t b")) => ["a \t b"])
              (fact "null char"
                    (into [] (tokenize "a \0 b")) => ["a \0 b"])
              (fact "everything"
                    (into [] (tokenize "n1\0\tbc\n \ta")) => ["n1\0\tbc" " \ta"]))
       (fact "optionally process words"
             (into [] (tokenize "A Bb" #(.toLowerCase %)))  => ["a bb"]))

(facts "incrementing values in a map"
       (fact "default increment of one"
             (increment-key {:a 1} :a) => {:a 2})
       (fact "leave the rest as it is"
             (increment-key {:a 1 :b 0} :a) => {:a 2 :b 0})
       (fact "create the key default to one"
             (increment-key {:a 1} :z) => {:a 1 :z 1})
       (fact "create the key incrementing it by the specified value"
             (increment-key {:a 1} :z 3) => {:a 1 :z 3})
       (fact "existing key is incremented by the specified value"
             (increment-key {:a 1} :a 3) => {:a 4}))

(facts "altering the counter maps"
       (fact "first line comes in"
             (reduce-f seed "a b c") => {:l 1 :w 3 :c 5 :f {"a" 1 "b" 1 "c" 1}})
       (fact "already present element is incremented"
             (reduce-f (reduce-f seed "a b c") "a") => {:l 2 :w 4 :c 6 :f {"a" 2 "b" 1 "c" 1}})
       (fact "it is perfectly happy to use the monoid"
             (reduce-f (combine-f) "a") => {:l 1 :w 1 :c 1 :f {"a" 1}}))

(facts "ordering sequences"
       (order-by-frequency {:a "a" :f {"a" 1 "b" 2}}) => {:a "a" :f (list ["b" 2] ["a" 1])})

(facts "counting words and frequencies"
       (fact "a single word string"
             (wf "a" :freq) => {:l 1 :w 1 :c 1 :f (list ["a" 1])}) 
       (fact "frequencies are ordered DESC by default"
             (first (:f (wf "a a a b b c" :freq))) => ["a" 3])
       (fact "divina commedia key facts"
             (let [divina (slurp (io/resource "divina-commedia.txt"))
                   res (wf divina :freq)]
               (first (:f res)) => ["e" 4862]
               res => (contains {:c 560110})
               res => (contains {:w 105526})
               res => (contains {:l 14723}))))

(facts "skipping frequencies"
       (fact "should not calculate frequencies"
             (:f (wf "a a a b b c")) => [])) 
 
;; long running acceptances
(facts "perfomances"
       (let [war (slurp (io/resource "war-and-peace.txt"))]
         (fact "sequential war and peace"
               (first (:f (time (sequential-wf war :freq)))) => ["the" 34721])
         (fact "parallel war and peace"
               (first (:f (time (wf war :freq)))) => ["the" 34721])
         (fact "sequential war and peace no frequencies"
               (:c (time (sequential-wf war))) => 3161608)
         (fact "parallel war and peace no frequencies"
               (:c (time (wf war))) => 3161608)))
