(ns pwc.tokenizer)

(defn isalpha? [c]
  (not (= \space c)))

(defn- tokenize* [s tokens]
  (let [word (take-while isalpha? s)
        res (conj tokens (apply str word))]
  (if (= (count word) (count s))
    res 
    (recur (drop 1 (drop-while isalpha? s)) res))))

(defn tokenize [^String s]
  (tokenize* (seq (clojure.string/trim s)) []))

(defn jtokenize [^String s]
  (seq (pwc.Tokenizer/tokenize s)))
