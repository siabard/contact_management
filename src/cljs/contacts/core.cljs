(ns contacts.core
  (:require [ajax.core :refer [GET]]
            [helix.core :refer [defnc $ <>]]
            [helix.dom :as d]
            ["react-dom/client" :as rdom]))

(defonce root (rdom/createRoot (.getElementById js/document "app")))

(defnc nav []
  (d/nav {:class "py-2 shadow"}
         (d/div {:class "container"}
                (d/h2 {:class "text-xl"} "Contact Book"))))

(defnc app []
  (<>
    ($ nav)))


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
