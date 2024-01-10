(ns training.week3.day5.problem77)

(defn is-anagram [word1 word2]
  (let [chars1 (set word1)
        chars2 (set word2)]
    (= chars1 chars2)))

(defn group-by-anagrams [words]
  (loop [words words
         total #{}]
    (let [word (first words)
          anagrams (set (concat (take 1 words) (filter #(is-anagram word %1) (drop 1 words))))]
      (cond 
        (nil? word) total
        (< (count anagrams) 2) (recur (rest words) total)
        :else (recur (filter #(not (anagrams %1)) words) (conj total anagrams))
      ))))

(defn solve1 []
  (= (group-by-anagrams ["meat" "mat" "team" "mate" "eat"])
   #{#{"meat" "team" "mate"}}))

(defn solve2 []
  (= (group-by-anagrams ["veer" "lake" "item" "kale" "mite" "ever"])
   #{#{"veer" "ever"} #{"lake" "kale"} #{"mite" "item"}}))

