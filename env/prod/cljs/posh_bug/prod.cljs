(ns posh-bug.prod
  (:require [posh-bug.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
