#!/usr/bin/env groovy

// Script to generate the .ant targets in the build directory
//   -> one file for each database flavor and one for each koan
// If you add a different database flavor or a new koan, just
// edit the dbs and koans variables at the top of the file.

// if you add another koan, increment the range
def koans = 1 .. 26;

def compdbs = ['h2', 'mysql', 'pg'];
def maindbs = ['h2', 'default'];

def tmplFile = new File('maven-run.ant.tmpl');

compdbs.each { db ->
  koans.each { k ->
    def n = leftPad(k)
    def f = new File("../comp/${db}/maven-run-comp-koan${n}-${db}.ant")
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

maindbs.each { db ->
  koans.each { k ->
    def n = leftPad(k)
    def f = new File("../main/${db}/maven-run-main-koan${n}-${db}.ant")
    f.withPrintWriter { w ->
      tmplFile.eachLine { line ->
        if (db == 'h2') {
          line = line.replaceAll(~/run-comp-koans-h2/,
                                 "run-koans-${db}")
        } else {
          line = line.replaceAll(~/run-comp-koans-h2/,
                                 "run-koans")
        }
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
