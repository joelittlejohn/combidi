(ns combidi.web
  (:require [bidi.ring :refer [make-handler]]
            [clojure.string :refer [join]]
            [compojure.core :refer [rfn]]
            [jry :refer [flatten-keys]]
            [mixradio.instrumented-jetty :refer [run-jetty]]
            [ring.middleware.params :refer [wrap-params]]))

(def routes
  ["/" {"say/" {"hi" (rfn [name] {:status 200 :body (str "hi " name)})
                "bye" (rfn [name] {:status 200 :body (str "bye " name)})}}])

(defn wrap-docs [handler routes]
  (fn [req]
    (if (= "/doc" (:uri req))
      {:status 200
       :body (->> routes second flatten-keys keys (map (partial apply str)) (join "\n"))}
      (handler req))))

(def app
  (-> (make-handler routes)
      wrap-params
      (wrap-docs routes)))

(def server (atom nil))

(defn start []
  (reset! server (run-jetty #'app {:port 8090 :join? false})))

(defn stop []
  (.stop @server))
