(ns client.panels.welcome
  (:require [client.utils :as utils :refer [l]]
            [client.routes :refer [navigate-to path-for]]
            [clojure.string :as str]
            [re-frame.core :as rf :refer [dispatch subscribe]]
            [reagent.core :as r]))

(defn welcome-panel []
  [:div#welcome-panel.panel.text-xs-center
   [:h1 "Lorem ipsum"]
   [:h4 "Lorem ipsum Lorem ipsu"]
   [:button#get-in.btn.btn-outline-primary {:on-click #(navigate-to :resources)}
    "Get onboard"]])
