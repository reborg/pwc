(ns pwc.t-word-freq
  (:use midje.sweet)
  (:use pwc.t-helper)
  (:use [pwc.word-freq]))

(facts "combining function construction"
       (fact "return the seed with no parameters"
             (combine-f) => seed)
       (fact "summing up result counters from different threads"
             (combine-f {:l 1 :w 1 :c 1 :f {}} {:l 1 :w 1 :c 1 :f {}}) => {:l 2 :w 2 :c 2 :f {}})
       (fact "frequencies are merged with +"
             (combine-f {:f {"a" 1 "b" 3}} {:f {"b" 5}}) => {:f {"a" 1 "b" 8}}))

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
             (reduce-freqs seed "a b c") => {:l 1 :w 3 :c 5 :f {"a" 1 "b" 1 "c" 1}})
       (fact "already present element is incremented"
             (reduce-freqs (reduce-freqs seed "a b c") "a") => {:l 2 :w 4 :c 6 :f {"a" 2 "b" 1 "c" 1}})
       (fact "it is perfectly happy to use the monoid"
             (reduce-freqs (combine-f) "a") => {:l 1 :w 1 :c 1 :f {"a" 1}}))

(facts "ordering sequences"
       (order-by-frequency {:a "a" :f {"a" 1 "b" 2}}) => {:a "a" :f (list ["b" 2] ["a" 1])})

(facts "counting words and frequencies"
       (fact "divina commedia key facts"
             (let [divina (iota-seq "divina-commedia.txt")
                   res (wf divina :freq)]
               (first (:f res)) => ["e" 4476]
               res => (contains {:c 580172})
               res => (contains {:w 105526})
               res => (contains {:l 20062}))))

(facts "skipping frequencies"
       (fact "should not calculate frequencies in parallel"
             (:f (wf (iota-seq "divina-commedia.txt"))) => [])
       (fact "should not calculate frequencies in sequential version"
             (:f (sequential-wf (iota-seq "divina-commedia.txt"))) => []))
 
;;(facts "perfomances (long running accepts)"
;;       (let [war (iota-seq "war-and-peace.txt")]
;;         (fact "sequential war and peace"
;;               (first (:f (time (sequential-wf war :freq)))) => ["the" 31950])
;;         (fact "parallel war and peace"
;;               (first (:f (time (wf war :freq)))) => ["the" 31950])
;;         (fact "sequential war and peace no frequencies"
;;               (:c (time (sequential-wf war))) => 3226615)
;;         (fact "parallel war and peace no frequencies"
;;               (:c (time (wf war))) => 3226615)))
