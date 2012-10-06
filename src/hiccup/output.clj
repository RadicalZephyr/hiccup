(ns hiccup.output
  "Renders nested maps representing a HTML DOM into a string."
  (:use hiccup.util))

(def ^{:doc "A list of tags that need an explicit ending tag when rendered."
       :private true}
  container-tags
  #{"a" "b" "body" "canvas" "dd" "div" "dl" "dt" "em" "fieldset" "form" "h1" "h2"
    "h3" "h4" "h5" "h6" "head" "html" "i" "iframe" "label" "li" "ol" "option"
    "pre" "script" "span" "strong" "style" "table" "textarea" "ul"})

(defn to-string
  ([node]
     (with-out-str
       (print (str "<" (as-str (:tag node))))
       (when-not (empty? (:attrs node))
         (doseq [[k v] (:attrs node)]
           (print (str " " (as-str k) "=\"" (escape-html v) "\""))))
       (print ">")
       (doseq [content (:content node)]
         (if (map? content)
           (to-string content)
           (print (escape-html content))))
       (print (str "</" (as-str (:tag node)) ">"))))
  ([n & nodes]
     (apply str (map to-string (cons n nodes)))))
