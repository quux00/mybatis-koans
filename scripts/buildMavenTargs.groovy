#!/usr/bin/env groovy

def tmplFile = new File('build/maven-run.ant.tmpl');

def dbs = ['h2', 'mysql', 'pg'];
def koans = 1 .. 23;

dbs.each { db ->
  koans.each { k ->
    def n = leftPad(k)
    def f = new File("build/maven-run-test-koan${n}-${db}.ant")
    f.withWriter { w ->
      tmplFile.eachLine { line ->
        line = line.replaceAll(~/run-test-koans-h2/,
                               "run-test-koans-${db}")
        line = line.replaceAll(~/koanName=Koan01/,
                               "koanName=Koan${n}")
        w.write(line)
      }
    }
  }
}


def leftPad(n) {
  if (n < 10) return "0" + n.toString()
  else        return n.toString()
}
