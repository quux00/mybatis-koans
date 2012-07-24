#!/usr/bin/env groovy

// Script to generate the .ant targets in the build directory
//   -> one file for each database flavor and one for each koan
// If you add a different database flavor or a new koan, just
// edit the dbs and koans variables at the top of the file.

def dbs = ['h2', 'mysql', 'pg'];
def koans = 1 .. 23;

def tmplFile = new File('build/maven-run.ant.tmpl');

dbs.each { db ->
  koans.each { k ->
    def n = leftPad(k)
    def f = new File("build/maven-run-comp-koan${n}-${db}.ant")
    f.withPrintWriter { w ->
      tmplFile.eachLine { line ->
        line = line.replaceAll(~/run-comp-koans-h2/,
                               "run-comp-koans-${db}")
        line = line.replaceAll(~/koan=Koan01/,
                               "koan=Koan${n}")
        w.println(line)
      }
    }
  }
}


def leftPad(n) {
  if (n < 10) return "0" + n.toString()
  else        return n.toString()
}
