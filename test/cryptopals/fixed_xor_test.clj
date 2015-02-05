(ns cryptopals.fixed-xor-test
  (:require [expectations :refer [expect]]
            [cryptopals.bytes :refer :all]
            [cryptopals.fixed-xor :refer :all]))

;; Fixed XOR

;; Write a function that takes two equal-length buffers and produces their XOR combination.
;; If your function works properly, then when you feed it the string:
;; 1c0111001f010100061a024b53535009181c
;; ... after hex decoding, and when XOR'd against:
;; 686974207468652062756c6c277320657965
;; ... should produce:
;; 746865206b696420646f6e277420706c6179

(expect
 "746865206b696420646f6e277420706c6179"
 (hexor "1c0111001f010100061a024b53535009181c" "686974207468652062756c6c277320657965"))

;; ; 0x61 = a
(expect
 (hexor "abc123" "616161")
 (hexor-single-byte "abc123" \a))
