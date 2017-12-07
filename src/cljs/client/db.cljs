(ns client.db)

(def default-db
  {:route/match {:panel        nil
                 :segment      nil
                 :params       nil
                 :query-params nil}
   :resource-types ["Coverage" "Patient" "Hospital"] ; mock
   :resources {"Coverage" []
               "Patient"  (take 20 (repeatedly (fn [] {:name "Thomas Li" :id (str (rand-int 100000000))})))
               "Hospital" (take 20 (repeatedly (fn [] {:name "Mercy H."  :id (str (rand-int 100000000))})))}
   
   :resource/new nil})
