(ns pwc.t-word-count
  (:use midje.sweet)
  (:use pwc.t-helper)
  (:use [pwc.word-count]))

(facts "merging java arrays"
       (fact "merging with plus"
             (seq (merge-array-with + (long-array [1]) (long-array [1]))) => (seq (long-array [2])))
       (fact "and no side effects?"
             (let [a1 (long-array [1])
                   res (merge-array-with + (long-array [1]) a1)]
               (seq a1) => (list 1))))

(facts "combinef"
       (fact "return the seed with no parameters"
             (combine-f) => seed)
       (fact "summing up result counters from different threads"
             (seq (combine-f (long-array [1 1 1]) (long-array [1 1 1]))) => (seq (long-array [2 2 2]))))

(facts "altering the counter array"
       (fact "first line comes in"
             (seq (reduce-counters seed "a b c")) => (seq (long-array [1 3 5])))
       (fact "already present element is incremented"
             (seq (reduce-counters (reduce-counters seed "a b c") "a")) => (seq (long-array [2 4 6])))
       (fact "it is perfectly happy to use the monoid"
             (seq (reduce-counters (combine-f) "a")) => (seq (long-array [1 1 1]))))

(facts "counting words and frequencies"
       (fact "divina commedia key facts"
             (let [divina (iota-seq "divina-commedia.txt")
                   res (wf divina)]
               res => (contains 580172)
               res => (contains 105521)
               res => (contains 20062))))

(facts "perfomances (long running accepts)"
       (let [war (iota-seq "war-and-peace.txt")]
         ;;(fact "sequential war and peace"
         ;;      (last (time (sequential-wf war))) => 3226615)
         (fact "parallel war and peace"
               (last (time (wf war))) => 3226615)))
