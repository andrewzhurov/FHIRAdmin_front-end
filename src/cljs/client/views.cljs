(ns client.views
  (:require [client.components :as comps]
            [client.panels.welcome :refer [welcome-panel]]
            [client.panels.resources :refer [resources-panel]]
            [client.panels.resource :refer [resource-panel]]
            [client.routes :refer [navigate-to]]
            [reagent.core :as r]
            [re-frame.core :as rf :refer [dispatch subscribe]]))

(defn home-panel []
  [comps/dashboard {:active #{:welcome}}
   [welcome-panel]])

(defmulti render-panel (fn [route] [(:panel route) (:segment route)]))

(defmethod render-panel [:welcome-panel nil]
  [_]
  (home-panel))

(defmethod render-panel [:resources nil]
  [_]
  [comps/dashboard {:active #{:home}}
   [resources-panel]])

(defmethod render-panel [:resources '(:Patient)]
  [params]
  [comps/dashboard {}
   [resource-panel params]])

(defmethod render-panel :default
  [_]
  (home-panel))


(defn main-panel []
  (let [active-route @(subscribe [:active-route])]
    [:div.root
     {:on-click #(do (rf/dispatch [:set :menus nil]))}
     
     (js/window.scrollTo 0 0)
     [render-panel active-route]]))
