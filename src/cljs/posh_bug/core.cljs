(ns posh-bug.core
  (:require [reagent.core :as r]
            [posh.reagent :refer [pull q posh! transact!]]
            [datascript.core :as d]))

(def schema {;; Contact component (shared across entities)
             :contact/email {:db/cardinality :db.cardinality/one}
             :contact/phone {:db/cardinality :db.cardinality/one}

             ;; Users
             :user/username {:db/unique      :db.unique/identity
                             :db/cardinality :db.cardinality/one}
             :user/contact  {:db/valueType   :db.type/ref
                             :db/cardinality :db.cardinality/one
                             :db/isComponent true}})
(def conn (d/create-conn schema))
(posh! conn)


(transact! conn [{:db/id         -1
                  :user/username "seantempesta"
                  :user/contact  {:db/id         -2
                                  :contact/phone "555-5555"
                                  :contact/email "sean.tempesta@gmail.com"}}])



;; -------------------------
;; Views

(defn home-page []
  (let [user (pull conn '[*] [:user/username "seantempesta"])
        phone (pull conn '[*] 2)]
    [:div [:h2 "Welcome to posh-bug!"]

     [:div [:h4 {} "User Entity"]
      [:h5 {} @user]]

     [:div [:h4 {} "Phone Entity"]
      [:h5 {} @phone]]

     [:input {:type     "button" :value "Remove phone number"
              :on-click #(transact! conn [[:db/retract 2 :contact/phone "555-5555"]])}]]))

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
