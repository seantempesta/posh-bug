(ns ^:figwheel-no-load posh-bug.dev
  (:require [posh-bug.core :as core]
            [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(core/init!)
