(ns client.components
  (:require-macros [commons.env :refer [cljs-env]])
  (:require [reagent.core :as r]
            [re-frame.core :as rf :refer [dispatch subscribe]]
            [client.routes :refer [path-for navigate-to]]
            [goog.string :as gstring]))

(defn dashboard [{:keys [active disabled]} content]
  (js/console.log "Dashboard params:" active disabled)
  [:div.dashboard
   [:div.side.left
    (doall
     (map (fn [{:keys [name class-name on-click]}]
            ^{:key (str "sidebar-icon-" name)}
            [:div.icon {:class (cond-> class-name
                                 (contains? active name) (str " active")
                                 (contains? disabled name) (str " disabled"))
                        :on-click on-click}])
          [{:name :welcome
            :class-name "leave"
            :on-click #(navigate-to :welcome-panel)}
           {:name :home
            :class-name "home"
            :on-click #(navigate-to :resources)}]))]
   [:div.content
    content]])
