(ns liberator-tutorial.core
  (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes ANY]]))

;; define a simple parameterized resource

(defresource parameter [txt]
  :available-media-types ["text/plain"]
  :handle-ok (fn [_] (format "The text is %s" txt)))

;; set up routes to our resources

(defroutes app
  (ANY "/foo" [] (resource :available-media-types ["text/html"]
                           :handle-ok "<html>Hello, Internet.</html>"))
  (ANY "/time" [] (resource :available-media-types ["text/html"]
                           :handle-ok (fn [ctx]
                                        (format "<html>It's %d milliseconds since the beginning of the epoch."
                                                (System/currentTimeMillis)))))
  (ANY "/foo/:txt" [txt] (parameter txt))
  (ANY "/choice" []
       (resource :available-media-types ["text/html"]
                 :exists? (fn [ctx]
                            (if-let [choice
                                     (get {"1" "stone" "2" "paper" "3" "scissors"}
                                          (get-in ctx [:request :params "choice"]))]
                              {:choice choice}))
                 :handle-ok (fn [ctx]
                              (format "<html>Your choice: &quot;%s&quot;."
                                      (get ctx :choice)))
                 :handle-not-found (fn [ctx]
                                     (format "<html>There is no value for the option &quot;%s&quot;"
                                             (get-in ctx [:request :params "choice"] ""))))))

(def handler 
  (-> app wrap-params))
