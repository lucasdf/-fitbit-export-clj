(ns fitbit-backup-clj.core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.string :as str]
            [clojure.tools.logging :as log]
            [clojure.tools.reader]
            [fitbit-backup-clj.oauth :as oauth]))

(def sleep-endpoint "https://api.fitbit.com/1.2/user/[user-id]/sleep/date/[date].json")
(defn sleep-data
  [user-id date token]
  (let [url-with-params (-> sleep-endpoint
                            (str/replace #"\[user-id\]" user-id)
                            (str/replace #"\[date\]" date))]
    (client/get url-with-params {:accepted :json
                                 :headers {"Authorization" (str "Bearer " token)}})))

(defn get-data
  [date]
  (let [{:keys [access_token refresh_token user_id] :as token} (oauth/token)]
    (log/info :token token)
    (json/read-str (:body (sleep-data user_id date access_token))
                   :key-fn keyword)))

(defn -main
  [& args]
  (print (json/write-str (get-data (first args)))))
