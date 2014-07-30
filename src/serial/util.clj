(ns serial.util
  (:require [serial.core :refer :all]))

(defn list-ports
  "Print out the available ports. The names are printed
   exactly as they should be passed to open."
  []
  (doall (map #(println (.getName %)) (port-ids))))

(defn- substring? [sub st]
  (not= (.indexOf st sub) -1))

(defn- arduino-port?
  "Compares port name with known arduino port formats"
  [port-name]
  (or
    (substring? "tty.usbmodem" port-name)    ;; Uno or Mega 2560
    (substring? "tty.usbserial" port-name))) ;; Older boards

(defn detect-arduino-port
  "Returns the first arduino serial port based on port
   name, or nil"
  []
  (first (filter arduino-port?
                 (map #(.getName %) (port-ids)))))
