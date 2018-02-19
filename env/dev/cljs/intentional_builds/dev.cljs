(ns ^:figwheel-no-load intentional-builds.dev
  (:require
    [intentional-builds.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
