(ns pwc.word-count
  (:require [clojure.string :as s]
            [clojure.core.reducers :as r]
            [pwc.tokenizer :as t]))

(def seed (long-array [0 0 0]))

(defn merge-array-with [f ^longs a1 ^longs a2]
  (amap a2 i res 
        (f (aget a1 i) (aget a2 i))))

(def combine-f
  (r/monoid (partial merge-array-with +) (constantly seed)))

(defn reduce-counters [acc ^String line]
   (let [tokens (t/jtokenize line)
         delta (long-array [1 (count tokens) (count (seq line))])]
     (merge-array-with + acc delta)))

(defn sequential-wf [fseq]
  (seq (reduce reduce-counters (combine-f) (r/filter identity fseq))))

(defn wc [fseq]
  (seq (r/fold 5000 combine-f reduce-counters (r/filter identity fseq))))
