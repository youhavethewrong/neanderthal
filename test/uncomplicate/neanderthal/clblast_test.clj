(ns uncomplicate.neanderthal.clblast-test
  (:require [midje.sweet :refer :all]
            [uncomplicate.clojurecl.core
             :refer [with-default *command-queue*]]
            [uncomplicate.neanderthal
             [opencl :refer [with-engine *opencl-factory*]]
             [block-test :as block-test]
             [real-test :as real-test]
             [device-test :as device-test]]
            [uncomplicate.neanderthal.internal.device.clblast :refer [clblast-float clblast-double]]))

(defn test-blas-clblast [factory]
  (real-test/test-imin factory)
  (real-test/test-imax factory)
  (real-test/test-ge-trans! factory)
  (real-test/test-ge-sum factory))

(with-default

  (with-engine clblast-float *command-queue*
    (block-test/test-all *opencl-factory*)
    (real-test/test-blas *opencl-factory*)
    (test-blas-clblast *opencl-factory*)
    (device-test/test-all *opencl-factory*))

  (with-engine clblast-double *command-queue*
    (block-test/test-all *opencl-factory*)
    (real-test/test-blas *opencl-factory*)
    (test-blas-clblast *opencl-factory*)
    (device-test/test-all *opencl-factory*)))
