(ns client.panels.resource
  (:require [client.utils :as utils :refer [l]]
            [client.routes :refer [navigate-to path-for]]
            [clojure.string :as str]
            [re-frame.core :as rf :refer [dispatch subscribe]]
            [reagent.core :as r]))

(defn resource-panel [params]
  (l "resource panel params" params)
  (let [res @(subscribe [:get-resource (name (first (:segment params))) (:id (:params params))])]
    [:div#resource-panel.panel
     [:div.horizontal-menu
      [:div#version-picker.menu-button.btn-group {:data-toggle "buttons"}
       [:label.btn.btn-primary.active
        [:input#option1
         {:checked "checked"
          :type "radio"}]
        "V1.0.2"]
       [:label.btn.btn-primary
        [:input#option2
         {:type "radio"}]
        "V3.0.2"]]
      [:button.menu-button.btn.btn-secondary "Cancel"]
      [:button.menu-button.btn.btn-primary "Save"]]
     
     [:div "Resource:" res]]))
