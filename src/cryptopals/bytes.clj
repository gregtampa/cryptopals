(ns cryptopals.bytes
  (:require [clojure.string :as string]))


(def hexchar->bin
  {
   \0 [0 0 0 0]
   \1 [0 0 0 1]
   \2 [0 0 1 0]
   \3 [0 0 1 1]
   \4 [0 1 0 0]
   \5 [0 1 0 1]
   \6 [0 1 1 0]
   \7 [0 1 1 1]
   \8 [1 0 0 0]
   \9 [1 0 0 1]
   \a [1 0 1 0]
   \b [1 0 1 1]
   \c [1 1 0 0]
   \d [1 1 0 1]
   \e [1 1 1 0]
   \f [1 1 1 1]})

(def hexchar->int
  (into {} (map hash-map "0123456789abcdef" (range 0 16))))

(def bin->hexchar
  (into {} (for [[key val] hexchar->bin] [val key])))

(def int->hexchar
  (into {} (for [[key val] hexchar->int] [val key])))


(defn zero-pad [desired-num binary-digits]
  (let [pad (- desired-num (rem (count binary-digits) desired-num))]
    (if (= pad desired-num)
      binary-digits
      (concat (repeat pad 0) binary-digits))))


(defn hexchars->int [hexchars]
  (if (= 1 (count hexchars))
    (hexchar->int (last hexchars))
    (+
     (* 16 (hexchar->int (first hexchars)))
     (hexchar->int (last hexchars)))))


(defn int->bin [number]
  (cond
   (< number 2) [number]
   :else (concat
          (int->bin (int (/ number 2)))
          [(mod number 2)])))


(defn hex->bin [hex-string]
  (reduce concat (map hexchar->bin hex-string)))


(defn two-to-the-n [n]
  (reduce * (repeat n 2N)))
(defn sixteen-to-the-n [n]
  (reduce * (repeat n 16N)))


(defn bin->int [binary-digits]
  (let [first-digit (first binary-digits)
        remainder (rest binary-digits)]
    (if (empty? binary-digits) 0
      (+ (* first-digit (two-to-the-n (count remainder)))
       (bin->int remainder)))))


(defn bin->hex [binary-digits]
  (let [grouped-bits (partition 4 (zero-pad 4 binary-digits))]
;;  (let [grouped-bits (reverse (map reverse (partition 4 4 (repeat 0) (reverse binary-digits))))]
    (string/join (map bin->hexchar grouped-bits))))

(defn- int->hexrep [number]
  (if (< number 16)
    (str (int->hexchar number))
    (str
     (int->hexrep (bigint (/ number 16N)))
     (int->hexchar (rem number 16)))))

(defn- singlechar? [string]
  (boolean (= 1 (count (take 2 string)))))

(defn int->hex [number]
  (let [hexrep (int->hexrep number)]
    (if (singlechar? hexrep) (str "0" hexrep) hexrep)))


(defn hexchars->char [hex-digit]
  (char (hexchars->int hex-digit)))

(defn hexstring->hexchars [hex-string]
  (map string/join (partition 2 hex-string)))

(defn hex->string [hex-string]
  (string/join (map hexchars->char (hexstring->hexchars hex-string))))


(defn char->hex [character]
  (string/join (int->hex (int character))))

(defn string->hex [string]
  (string/join (map char->hex string)))
