(ns client.routes
  (:require [bidi.bidi :as bidi]
            [commons.utils :as utils]
            [client.utils :refer [l]]
            [pushy.core :as pushy]
            [re-frame.core :as rf]))

(def routes
  ["/" {"" :welcome-panel
        "resources" {"/"        :resources ;; PROP Generation of routes/resources itself   can be added
                     "/Patient/" {[:id] :resources.Patient}}}
   ])

(defn set-page! [match]
  (let [[panel & segment] (utils/keyword-to-path (:handler match))]
    (rf/dispatch [:set-route panel segment (:route-params match) (:query-params match)])))

(defn parse-query-params [uri]
  ; "?a=1&&b=&c=2" -> {"a" ["1"], "" [""], "b" [""], "c" ["2"]}
  (let [query-data (.getQueryData (goog.Uri.parse uri)) ks (js->clj (.getKeys query-data))] (into {} (map #(do [% (js->clj (.getValues query-data %))])) ks)))

(def history
  (pushy/pushy set-page! (fn [uri]
                           (let [result ((partial bidi/match-route routes) uri)
                                 result
                                 (when result
                                  (assoc
                                    result
                                    :query-params (parse-query-params uri)))]
                             result))))

(defn app-routes []
  (pushy/start! history))

(def path-for (partial bidi/path-for routes))

(defn navigate-to [route-name & params]
  (pushy/set-token! history (apply path-for route-name params)))
