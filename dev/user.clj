(ns user
  (:require [clojure.repl :refer :all]
            [figwheel-sidecar.repl-api :refer :all]))

(defn cljs-start []
  (start-figwheel!)
  (cljs-repl))
