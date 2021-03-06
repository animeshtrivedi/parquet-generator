/*
 * parqgen: Parquet file generator for a given schema
 *
 * Author: Animesh Trivedi <atr@zurich.ibm.com>
 *
 * Copyright (C) 2016, IBM Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ibm.crail.spark.tools

import org.apache.spark.sql.SparkSession

/**
  * Created by atr on 11.10.16.
  */
object ParqReader {

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + b)

  def main(args: Array[String]) {
    println("ParqReader : " + foo(args))
    if(args.length != 1) {
      System.err.println(" Tell me which file to read? ")
      System.exit(-1)
    }

    val spark = SparkSession
      .builder()
      .appName("ParqReader: Spark SQL parquet, read, and count example")
      .getOrCreate()

    val inputDF = spark.read.parquet(args(0))
    val items = inputDF.count()
    val partitions = SparkTools.countNumPartitions(spark, inputDF)
    inputDF.show()
    println("----------------------------------------------------------------")
    println("RESULTS: file " + args(0) + " contains " + items + " rows and makes " + partitions + " partitions when read.")
    println("----------------------------------------------------------------")
    spark.stop()
  }
}
