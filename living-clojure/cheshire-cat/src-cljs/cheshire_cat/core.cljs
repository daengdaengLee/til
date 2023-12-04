(ns cheshire-cat.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [clojure.browser.repl :as repl]))

(defn ^:export init []
  (repl/connect "http://localhost:9000/repl")
  (go (let [response (<! (http/get "/cheshire-cat"))
            body (:body response)
            cat-name-content (:name body)
            status-content (:status body)
            cat-name-el (.getElementById js/document "cat-name")
            status-el (.getElementById js/document "status")]
        (set! (.-innerHTML cat-name-el) cat-name-content)
        (set! (.-innerHTML status-el) status-content)
        (set! (.-fontSize (.-style status-el)) "500%"))))
