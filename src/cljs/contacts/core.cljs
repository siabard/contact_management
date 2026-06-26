(ns contacts.core
  (:require [ajax.core :refer [GET]]
            [helix.core :refer [defnc $ <>]]
            [helix.hooks :as hooks]
            [helix.dom :as d]
            ["react-dom/client" :as rdom]
            [contacts.components.nav :refer [nav]]
            [contacts.components.contact-list :refer [contact-list]]
            [contacts.components.contact-form :refer [contact-form]]
            ))

(defonce root (rdom/createRoot (.getElementById js/document "app")))

(defnc app []
  (let [[state set-state] (hooks/use-state nil)]
    (hooks/use-effect
     :once
     (GET "http://localhost:4000/api/contacts/" 
          {
           :handler (fn [response] 
                      (set-state response))
           :response-format :json
           :keywords? true
           }))
    (if state
      (<>
       ($ nav)
       (d/div {:class "container pt-4"}
              ($ contact-list {:contacts state})
              ($ contact-form {:contact (first state)}))))))


(defn render []
  (.render root ($ app)))

(defn ^:export init 
  []
  (render))

(defn ^:dev/after-load reload 
  []
  (render))

(comment
  (GET "http://localhost:4000/api/contacts/" 
       {
        :handler (fn [response] (.log js/console response))
        })
)
