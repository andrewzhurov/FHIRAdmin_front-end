(ns commons.utils)

(defn keyword-to-path
  ":a.b.c/d.e.f -> [:a :b :c :d :e :f]"
  [kw]
  (mapv keyword (mapcat #(clojure.string/split % #"\.") (remove nil? ((juxt namespace name) kw)))))
