(ns client.core
  (:require
   [ring.adapter.jetty :refer [run-jetty]]

   [compojure
    [core :refer :all]
    [route :as route]]
   [ring.util.response :refer [resource-response]])
  (:gen-class))

(defroutes app
  (route/resources "/")
  (GET "/*" [] (resource-response "index.html" {:root "public"})))

(defn -main [& args]
  (run-jetty app {:port 3100 :join? false}))
