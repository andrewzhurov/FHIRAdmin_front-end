(ns client.panels.resources
  (:require [client.utils :as utils :refer [l]]
            [client.routes :refer [navigate-to path-for]]
            [clojure.string :as str]
            [re-frame.core :as rf :refer [dispatch subscribe]]
            [reagent.core :as r]))

(defmulti resource-label identity)
(defmethod resource-label "Patient"
  [_ res]
  [:div.resource
   [:div.name (:name res)]])
(defmethod resource-label :default
  [_ res]
  [:div.resource
   [:div.name "Unknown resource"]])

(defn resource-type-section [type]
  (let [resources @(subscribe [:get [:resources type]])]
    [:div.resource-type-section {:id type}
     [:div.type-title
      [:div.name type]
      [:div.icon.add-button]]
     [:div.resources-list
      (for [{:keys [id] :as res} resources]
        ^{:key (str "type-" type "-id-" id)}
        [:div#resource-label
         {:on-click #(navigate-to (keyword (str "resources." type)) :id id)}
         (resource-label type res)])]]))

(defn resources-panel []
  (l @(subscribe [:get []]))
  [:div#resources-panel.panel
   (for [resource-type @(subscribe [:get [:resource-types]])]
     ^{:key resource-type}
     [resource-type-section resource-type])])
