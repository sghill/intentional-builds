(ns intentional-builds.prod
  (:require
    [intentional-builds.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
