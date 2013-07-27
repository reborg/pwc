(ns pwc.word-count
  (:use midje.sweet))

(defn order-by-frequency [m]
  "return copy of hashmap m ordered by values descending.
  Assumes values are numeric values comparable with >"
  (sort-by last > m))
   

(defn tokenize [text]
  (into [] (re-seq #"\w+" text)))

(defn inc-or-add [m e]
  (assoc m e (inc (get m e 0))))

(defn wc [text]
    (order-by-frequency (reduce inc-or-add {} (tokenize text))))
