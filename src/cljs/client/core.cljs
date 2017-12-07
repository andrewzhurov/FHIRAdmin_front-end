(ns client.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as rf]
              [client.events]
              [client.subs]
              [client.config :as config]
              [client.routes :as routes]
              [client.views :as views]))


(defn dev-setup []
  (enable-console-print!)
  (when config/debug?
    (println "dev mode")))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (dev-setup)
  (routes/app-routes)
  (rf/dispatch-sync [:initialize-db])
  (rf/dispatch-sync [:load-defaults])
  (mount-root))
