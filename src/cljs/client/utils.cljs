(ns client.utils
  (:require-macros [commons.env :refer [cljs-env]])
  (:require [ajax.core :as ajax]))


(defn back-end-url []
  (cljs-env :back-end-url))

(defn l [& rest]
  (apply js/console.log "l:" rest)
  (first rest))

(defn json-request ;; TODO send in edn
  "Returns a http-xhrio request map."
  [{:keys [method uri params on-success on-failure token]}]
  (cond-> {:method method
           :uri uri
           :params params
           :format (ajax/json-request-format)
           :response-format (ajax/json-response-format {:keywords? true})
           :on-success on-success
           :on-failure on-failure}
    token (merge {:headers {"X-Shryne-Token" token}})))
