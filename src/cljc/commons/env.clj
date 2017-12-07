(ns commons.env
  (:require [environ.core :refer [env]]))

(defmacro cljs-env
  "A macro to access env variables at compile time."
  [k]
  (env k))
