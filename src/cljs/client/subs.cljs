(ns client.subs
  (:require [re-frame.core :as rf :refer [reg-sub subscribe]]))

(reg-sub
 :active-route
 (fn [db _]
   (:route/match db)))

(reg-sub
 :get
 (fn [db [_ k]]
   (let [path (if (sequential? k) k [k])]
     (get-in db path))))

(reg-sub
 :get-resource
 (fn [[_ type id] _]
   [(subscribe [:get [:resources type]])])
 (fn [[resources] [_ _ id]]
   (some (fn [res] (= (:id res) id) res)
         resources)))
