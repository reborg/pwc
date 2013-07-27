(ns pwc.word-count
  (:require [clojure.core.reducers :as r]))

(def hash-monoid
  (r/monoid (partial merge-with +) hash-map))

(defn order-by-frequency [m]
  "return copy of hashmap m ordered by values descending.
  Assumes values are numeric values comparable with >"
  (sort-by last > m))
   

(defn tokenize [text]
  (into [] (re-seq #"\w+" text)))

(defn inc-or-add [m e]
  (assoc m e (inc (get m e 0))))


(defn wc [text]
    (order-by-frequency 
      (r/fold hash-monoid inc-or-add (tokenize text))))
