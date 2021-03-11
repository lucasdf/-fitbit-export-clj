(ns fitbit-backup-clj.oauth
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.java.browse :as browse]
            [clojure.string :as str]
            [clojure.tools.logging :as log]
            [fitbit-backup-clj.config :as config]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.codec :as codec]))

(def *token* (atom nil))

(defn access-token
  [code]
  (let [client-id (:client-id config/config)
        client-secret (:client-secret config/config)
        url "https://api.fitbit.com/oauth2/token"
        resp (client/post url {:form-params {:code code :client_id client-id :grant_type "authorization_code" :redirect_uri "http://localhost:8080/auth/callback"}
                               :headers {"Authorization" (str "Basic " (codec/base64-encode (.getBytes (str client-id ":" client-secret))))}})]
    (json/read-str (:body resp) :key-fn keyword)))

(defn oauth-callback
  [code]
  (let [token (access-token code)]
    (reset! *token* token)
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/json-str {:code code :token token})}))

(defn local-server
  []
  (jetty/run-jetty
    (-> (fn [{:keys [params] :as req}]
          (case (:uri req)
            "/auth/callback"
              (oauth-callback (:code params))
            {:status 200 :headers {"Content-Type" "application/json"} :body "{\"status\": \"success\"}"}))
        (wrap-keyword-params)
        (wrap-params {:encoding "UTF-8"}))
    {:host "127.0.0.1" :port 8080 :join? false}))

(defn open-authorization-page
  []
  (let [client-id (:client-id config/config)
        redirect-url (codec/url-encode "http://localhost:8080/auth/callback")
        base-url "https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=[client-id]&redirect_uri=[redirect-url]&scope=activity%20nutrition%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight"
        url (-> base-url
                (str/replace #"\[client-id\]" client-id)
                (str/replace #"\[redirect-url\]" redirect-url))]
    (browse/browse-url url)))

(defn token
  []
  (let [server (local-server)
        p (promise)]
    (add-watch *token* :watch-changed
               (fn [_ _ old new]
                 (when-not (= old new) (deliver p :changed))))
    (open-authorization-page)
    (deref p)
    (.stop server)
    @*token*))

(comment
  (def server (local-server))
  (.stop @server)
  (.start @server))
