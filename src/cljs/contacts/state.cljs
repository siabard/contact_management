(ns contacts.state
  (:require [helix.core :refer [create-context]]
            [helix.hooks :as hooks]))

(def initial-state {:selected nil
                    :contacts []})

(def app-state (create-context nil))

(defmulti app-reducer 
  (fn [_ action]
    (first action)))

(defmethod app-reducer 
  ::set-contacts [state [_ payload]]
  (assoc state :contacts payload))


(defmethod app-reducer
  ::set-selected [state [_ payload]]
  (assoc state :selected payload))

(defmethod app-reducer
  ::add-contact [state [_ payload]]
  (let [prev (:contacts state)
        next (conj prev payload)]
    (assoc state :contacts next)))

(defmethod app-reducer
  ::update-contact [state [_ payload]]
  (let [prev (:contacts state)
        update-contact #(if (= (:id %) (:id payload))
                          payload
                          %)
        next (map update-contact prev)]
    (assoc state :contacts next)))

(defmethod app-reducer
  ::remove-contact [state [_ payload]]
  (let [prev (:contacts state)
        not-matching (fn [item]
                       (not (= (:id item) (:id payload))))
        next (filter not-matching prev)]
    (assoc state :contacts next)))

(comment
  (app-reducer initial-state [::set-contacts []])
)

(defn use-app-state []
  (let [[state dispatch] (hooks/use-context app-state)]
    [state {:init (fn [response]
                    (dispatch [::set-contacts response])
                    (dispatch [::set-selected (first response)]))
            :add-contact #(dispatch [::add-contact %])
            :update-contact #(dispatch [::update-contact %])
            :delete-contact #(dispatch [::remove-contact %])}])) 
