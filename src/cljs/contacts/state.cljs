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

(comment
  (app-reducer initial-state [:set-contacts []])
)

(defn use-app-state []
  (let [[state dispatch] (hooks/use-context app-state)]
    [state {:init (fn [response]
                    (dispatch [::set-contacts response])
                    (dispatch [::set-selected (first response)]))}]))
