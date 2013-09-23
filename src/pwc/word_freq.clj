(ns pwc.word-freq
  (:require [clojure.string :as s]
            [clojure.core.reducers :as r]
            [pwc.tokenizer :as t]))

(def seed {:l 0 :w 0 :c 0 :f {}})

(defn deep-merge-with
  "Like merge-with, but merges maps recursively. If vals are not maps,
  (apply f vals) determines the winner."
  [f & vals]
  ;;(let [p1 (println "1: " (:f (first vals)))
  ;;      p2 (println "2: " (:f (second vals)))]
    (letfn [(m [& vals]
              (when (some identity vals)
                (if (every? map? vals)
                  (apply merge-with m vals)
                  (apply f vals))))]
      (apply m vals)))

(def combine-f
  (r/monoid (partial deep-merge-with +) (constantly seed)))

(defn order-by-frequency [m]
  "return copy of the map m n which the key :f was ordered by values descending.
  Assumes values are numeric values comparable with >"
  (assoc m :f (sort-by last > (:f m))))
   
(defn increment-key 
  "Work on associative structure m, fetching the given key if exists and
  incrementing the integer value of the key by 1. If key does not exist adds
  key with value 1. Accept optional value if required increment is different from one."
  ([m k]
    (increment-key m k 1))
  ([m k v]
    (assoc m k (+ v (get m k 0)))))

(defn reduce-counters 
  ([counters new-line]
   (reduce-counters counters new-line (count (t/jtokenize new-line))))
  ([counters new-line token-count]
   (-> counters 
       (increment-key :l)
       (increment-key :w token-count)
       (increment-key :c (count (seq new-line))))))

(defn reduce-freqs [counters new-line]
  (let [tokens (t/jtokenize new-line)
        new-counters (reduce-counters counters new-line (count tokens))
        new-freqs (reduce #(increment-key %1 %2) (:f counters) tokens)]
    (assoc new-counters :f new-freqs)))

(defn sequential-wf 
  ([fseq]
   (assoc (order-by-frequency (reduce reduce-counters (combine-f) (r/filter identity fseq))) :f []))
  ([fseq freq]
   (order-by-frequency (reduce reduce-freqs (combine-f) (r/filter identity fseq)))))

(defn wf 
  ([fseq]
     (assoc (r/fold 5000 combine-f reduce-counters (r/filter identity fseq)) :f []))
  ([fseq freq]
     (order-by-frequency (r/fold 5000 combine-f reduce-freqs (r/filter identity fseq)))))
