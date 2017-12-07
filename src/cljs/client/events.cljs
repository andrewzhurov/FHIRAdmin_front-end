(ns client.events
  (:require [ajax.core :as ajax]
            [akiroz.re-frame.storage :refer [reg-co-fx! persist-db]]
            [client.db :as db]
            [client.routes :as routes]
            
            [client.utils :as utils]
            day8.re-frame.http-fx
            [re-frame.core :as rf :refer [reg-event-db reg-event-fx reg-fx reg-cofx inject-cofx ->interceptor]]))

(defn resource->url [{:keys [type id]}]
  (str (utils/back-end-url) "/resources/" (name type) (when id (str "/" id))))

(reg-event-fx
 :resources/read
 (fn [{:keys [db]} [_]]
   {:http-xhrio {:method :get
                 :uri (str backend-url "/resources")
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:resources.get/success]}}))

(reg-event-db
 :resources.read/success
 (fn [db [_ response]]
   (assoc db :resources-info response)))


(reg-event-fx
 :resource/read
 (fn [{:keys [db]} [resource-info]]
   {:http-xhrio {:method :get
                 :uri (resource->url resource-info)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:resource.read/success]}}))

(reg-event-db
 :resources.read/success
 (fn [db [_ response]]
   (update :resources conj response)))


(reg-event-fx
 :resource/create
 (fn [{:keys [db]} [resource-info]]
   {:http-xhrio {:method :post
                 :uri (resource->url resource-info)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:load-resources/success]}}))


;; storage declaration, :storage is db key.
(reg-co-fx!
 :storage
 {:fx   :store
  :cofx :store})


(reg-event-fx
 :load-defaults
 [(inject-cofx :store)]
 (fn [{:keys [db store]} _]
   {:store store}))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))


(def on-panel-open
  {:home-panel {:dispatch-n [[:payment/reset-to-origin]
                             [:load-account-plans-if-needed]
                             [:load-countries-if-needed]]}
   })

(reg-event-fx
 :set-route
 (fn [{db :db} [_ panel segment params query-params]]
   (merge
    {:db (assoc db :route/match {:panel   panel
                                 :segment segment
                                 :params  params
                                 :query-params query-params})}
    (get on-panel-open panel))))

(reg-fx
 :navigate-to
 (fn [{:keys [route params] :or {params []}}]
   (apply routes/navigate-to route params)))

(reg-event-db
 :set
 (fn [db [_ k v]]
   (let [path (if (sequential? k) k [k])]
    (assoc-in db path v))))
