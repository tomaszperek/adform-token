(ns loginscript.core
  (:gen-class)
  (:require [http.async.client :as http]
            [clojure.data.json :as json]
            ))


(defn -main [env username password client-id campaign-id]
  (with-open [client (http/create-client)]
    (let [response (http/POST
                     client
                     (str "http://" env ":50054/v1/auth/login")
                     :body (json/write-str {:username username :password password})
                     :headers {:content-type "application/json"}
                     )
          ticket (-> response
                     http/await
                     http/string
                     json/read-str
                     (get-in ["AuthTicket" "Ticket"])
                     )
          _ (->
              (http/POST
                client
                (str "http://" env ":50064/websession/params")
                :body (json/write-str {:clientId client-id :campaignId campaign-id})
                :headers {
                          :content-type "application/json"
                          :Ticket       ticket
                          }
                )
              http/await
              )
          set-client-campaign-get (-> (http/GET
                                        client
                                        (str "http://" env ":50064/websession/params")
                                        :headers {:Ticket ticket})
                                      http/await
                                      http/string
                                      )]
      (println "\n\nTicket: \n" ticket "\n\n" set-client-campaign-get)
      )
    )
  )
