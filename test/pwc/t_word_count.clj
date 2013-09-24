(ns pwc.t-word-count
  (:use midje.sweet)
  (:use pwc.t-helper)
  (:use [pwc.word-count]))

(fact "merging counters"
             (mergewith + [1] [1]) => [2]
             (mergewith + [1 2 3] [1 3 5]) => [2 5 8])

(facts "combinef"
       (fact "return the seed with no parameters"
             (combine-f) => seed)
       (fact "summing up result counters from different threads"
             (combine-f [1 1 1] [1 1 1]) => [2 2 2]))

(facts "altering the counter array"
       (fact "first line comes in"
             (reduce-counters seed "a b c") => [1 3 5])
       (fact "already present element is incremented"
             (reduce-counters (reduce-counters seed "a b c") "a") => [2 4 6])
       (fact "it is perfectly happy to use the monoid"
             (reduce-counters (combine-f) "a") => [1 1 1]))

(facts "counting words and frequencies"
       (fact "divina commedia key facts"
             (let [divina (iota-seq "divina-commedia.txt")
                   res (wc divina)]
               res => (contains 580172)
               res => (contains 105521)
               res => (contains 20062))))

(facts "perfomances (long running accepts)"
       (let [war (iota-seq "war-and-peace.txt")]
         ;;(fact "sequential war and peace"
         ;;      (last (time (sequential-wf war))) => 3226615)
         (fact "parallel war and peace"
               (last (time (wc war))) => 3226615)))
