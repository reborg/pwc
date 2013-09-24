(ns pwc.word-count
  (:require [clojure.string :as s]
            [clojure.core.reducers :as r]
            [pwc.tokenizer :as t]))

(def seed [0 0 0])

(defn mergewith [f v1 v2]
  (vec (map + v1 v2)))

(def combine-f
  (r/monoid (partial mergewith +) (constantly seed)))

(defn reduce-counters [acc line]
   (let [tokens (t/jtokenize line)
         delta [1 (count tokens) (count (seq line))]]
     (mergewith + acc delta)))

(defn sequential-wf [fseq]
  (seq (reduce reduce-counters (combine-f) (r/filter identity fseq))))

(defn wc [fseq]
  (seq (r/fold 5000 combine-f reduce-counters (r/filter identity fseq))))
