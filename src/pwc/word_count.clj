(ns pwc.word-count
  (:use midje.sweet))

(defn tokenize [text]
  (into [] (re-seq #"\w+" text)))

(defn inc-or-add [m e]
  (assoc m e (inc (get m e 0))))

(defn wc [text]
    (reduce inc-or-add {} (tokenize text)))
